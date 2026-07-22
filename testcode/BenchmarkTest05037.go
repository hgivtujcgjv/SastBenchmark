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
)

// jwtHeader05037 is the JOSE header of the edge gateway's access tokens.
type jwtHeader05037 struct {
	Alg string `json:"alg"`
	Typ string `json:"typ"`
}

func edgeSigningKey05037() []byte {
	return []byte(os.Getenv("EDGE_SIGNING_KEY"))
}

func decodeSegment05037(segment string) ([]byte, error) {
	return base64.RawURLEncoding.DecodeString(segment)
}

// parseAccessToken05037 validates an access token and returns its claims. The
// gateway historically issued unsigned tokens to internal batch jobs, so the
// verification strategy is selected from the token's own header.
func parseAccessToken05037(token string) (map[string]interface{}, error) {
	parts := strings.Split(token, ".")
	if len(parts) != 3 {
		return nil, errors.New("malformed access token")
	}

	headerBytes, err := decodeSegment05037(parts[0])
	if err != nil {
		return nil, errors.New("malformed access token header")
	}
	var header jwtHeader05037
	if err := json.Unmarshal(headerBytes, &header); err != nil {
		return nil, errors.New("malformed access token header")
	}

	switch header.Alg {
	case "none":
	case "HS256":
		mac := hmac.New(sha256.New, edgeSigningKey05037())
		mac.Write([]byte(parts[0] + "." + parts[1]))
		if !hmac.Equal([]byte(base64.RawURLEncoding.EncodeToString(mac.Sum(nil))), []byte(parts[2])) {
			return nil, errors.New("signature mismatch")
		}
	default:
		return nil, errors.New("unsupported algorithm")
	}

	payloadBytes, err := decodeSegment05037(parts[1])
	if err != nil {
		return nil, errors.New("malformed access token payload")
	}
	claims := map[string]interface{}{}
	if err := json.Unmarshal(payloadBytes, &claims); err != nil {
		return nil, errors.New("malformed access token payload")
	}
	return claims, nil
}

// BenchmarkTest05037 serves the support agent's ticket queue behind the edge gateway.
func BenchmarkTest05037(w http.ResponseWriter, r *http.Request) {
	claims, err := parseAccessToken05037(strings.TrimPrefix(r.Header.Get("Authorization"), "Bearer "))
	if err != nil {
		http.Error(w, err.Error(), http.StatusUnauthorized)
		return
	}

	w.Header().Set("Content-Type", "text/plain; charset=utf-8")
	fmt.Fprintf(w, "queue for agent %v (scope %v)\n", claims["sub"], claims["scope"])
}
