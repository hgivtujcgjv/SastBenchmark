// OWASP Benchmark-style Go case.
package testcode

import (
	"crypto/hmac"
	"crypto/sha256"
	"encoding/base64"
	"encoding/json"
	"errors"
	"fmt"
	"net/http"
	"os"
	"strings"
	"time"
)

// apiClaims05040 is the payload of the machine-to-machine API tokens.
type apiClaims05040 struct {
	Subject string `json:"sub"`
	Scope   string `json:"scope"`
	Expires int64  `json:"exp"`
}

func apiSigningKey05040() ([]byte, error) {
	secret := os.Getenv("API_TOKEN_HMAC_KEY")
	if secret == "" {
		return nil, errors.New("API_TOKEN_HMAC_KEY is not configured")
	}
	return []byte(secret), nil
}

// parseAPIToken05040 verifies a machine-to-machine token and returns its claims.
func parseAPIToken05040(token string, key []byte) (*apiClaims05040, error) {
	parts := strings.Split(token, ".")
	if len(parts) != 3 {
		return nil, errors.New("malformed api token")
	}

	signature, err := base64.RawURLEncoding.DecodeString(parts[2])
	if err != nil {
		return nil, errors.New("malformed api token signature")
	}

	mac := hmac.New(sha256.New, key)
	mac.Write([]byte(parts[0] + "." + parts[1]))
	if !hmac.Equal(mac.Sum(nil), signature) {
		return nil, errors.New("signature mismatch")
	}

	payload, err := base64.RawURLEncoding.DecodeString(parts[1])
	if err != nil {
		return nil, errors.New("malformed api token payload")
	}
	var claims apiClaims05040
	if err := json.Unmarshal(payload, &claims); err != nil {
		return nil, errors.New("malformed api token payload")
	}

	if claims.Expires == 0 || time.Now().After(time.Unix(claims.Expires, 0)) {
		return nil, errors.New("api token expired")
	}
	return &claims, nil
}

// BenchmarkTest05040 exposes the export job status endpoint of the partner API.
func BenchmarkTest05040(w http.ResponseWriter, r *http.Request) {
	key, err := apiSigningKey05040()
	if err != nil {
		http.Error(w, "token verification is unavailable", http.StatusServiceUnavailable)
		return
	}

	claims, err := parseAPIToken05040(strings.TrimPrefix(r.Header.Get("Authorization"), "Bearer "), key)
	if err != nil {
		http.Error(w, err.Error(), http.StatusUnauthorized)
		return
	}
	if !strings.Contains(claims.Scope, "exports:read") {
		http.Error(w, "insufficient scope", http.StatusForbidden)
		return
	}

	w.Header().Set("Content-Type", "text/plain; charset=utf-8")
	fmt.Fprintf(w, "export jobs for client %s: 3 queued, 1 running\n", claims.Subject)
}
