// OWASP Benchmark-style Go case.
package testcode

import (
	"bytes"
	"crypto/hmac"
	"crypto/sha256"
	"encoding/base64"
	"encoding/json"
	"errors"
	"fmt"
	"net/http"
	"os"
	"strings"
)

// sessionKeyPath05039 points at the key material mounted by the deployment secret.
const sessionKeyPath05039 = "/etc/acme/session-signing.key"

func sessionSigningKey05039() ([]byte, error) {
	raw, err := os.ReadFile(sessionKeyPath05039)
	if err != nil {
		return nil, errors.New("session signing key is unavailable")
	}
	key := bytes.TrimSpace(raw)
	if len(key) == 0 {
		return nil, errors.New("session signing key is empty")
	}
	return key, nil
}

// verifySessionToken05039 checks a session token and returns its claims. The service
// only ever issues HS256 tokens, so that is the only shape it accepts back.
func verifySessionToken05039(token string, key []byte) (map[string]string, error) {
	parts := strings.Split(token, ".")
	if len(parts) != 3 {
		return nil, errors.New("malformed session token")
	}

	signature, err := base64.RawURLEncoding.DecodeString(parts[2])
	if err != nil {
		return nil, errors.New("malformed session token signature")
	}

	mac := hmac.New(sha256.New, key)
	mac.Write([]byte(parts[0] + "." + parts[1]))
	if !hmac.Equal(mac.Sum(nil), signature) {
		return nil, errors.New("signature mismatch")
	}

	payload, err := base64.RawURLEncoding.DecodeString(parts[1])
	if err != nil {
		return nil, errors.New("malformed session token payload")
	}
	claims := map[string]string{}
	if err := json.Unmarshal(payload, &claims); err != nil {
		return nil, errors.New("malformed session token payload")
	}
	return claims, nil
}

// BenchmarkTest05039 serves the "who am I" endpoint used by the web console shell.
func BenchmarkTest05039(w http.ResponseWriter, r *http.Request) {
	key, err := sessionSigningKey05039()
	if err != nil {
		http.Error(w, "session verification is unavailable", http.StatusServiceUnavailable)
		return
	}

	claims, err := verifySessionToken05039(strings.TrimPrefix(r.Header.Get("Authorization"), "Bearer "), key)
	if err != nil {
		http.Error(w, err.Error(), http.StatusUnauthorized)
		return
	}

	w.Header().Set("Content-Type", "text/plain; charset=utf-8")
	fmt.Fprintf(w, "signed in as %s in tenant %s\n", claims["sub"], claims["tenant"])
}
