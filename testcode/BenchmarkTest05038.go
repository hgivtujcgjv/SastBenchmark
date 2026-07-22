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
	"strings"
)

// mobileTokenSecret05038 is the HMAC secret the mobile app and this service share.
const mobileTokenSecret05038 = "8f2a1c9e-mobile-hmac-2019-prod"

// tokenVerifier05038 verifies the HS256 device tokens minted by the mobile backend.
type tokenVerifier05038 struct {
	secret []byte
}

var mobileVerifier05038 = tokenVerifier05038{secret: []byte(mobileTokenSecret05038)}

func (v tokenVerifier05038) verify05038(token string) (map[string]string, error) {
	parts := strings.SplitN(token, ".", 3)
	if len(parts) != 3 {
		return nil, errors.New("malformed device token")
	}

	mac := hmac.New(sha256.New, v.secret)
	mac.Write([]byte(parts[0] + "." + parts[1]))
	signature, err := base64.RawURLEncoding.DecodeString(parts[2])
	if err != nil {
		return nil, errors.New("malformed device token signature")
	}
	if !hmac.Equal(mac.Sum(nil), signature) {
		return nil, errors.New("signature mismatch")
	}

	payload, err := base64.RawURLEncoding.DecodeString(parts[1])
	if err != nil {
		return nil, errors.New("malformed device token payload")
	}
	claims := map[string]string{}
	if err := json.Unmarshal(payload, &claims); err != nil {
		return nil, errors.New("malformed device token payload")
	}
	return claims, nil
}

// BenchmarkTest05038 returns the push-notification registration of the calling device.
func BenchmarkTest05038(w http.ResponseWriter, r *http.Request) {
	claims, err := mobileVerifier05038.verify05038(strings.TrimPrefix(r.Header.Get("Authorization"), "Bearer "))
	if err != nil {
		http.Error(w, err.Error(), http.StatusUnauthorized)
		return
	}

	w.Header().Set("Content-Type", "text/plain; charset=utf-8")
	fmt.Fprintf(w, "device %s registered for account %s\n", claims["device_id"], claims["sub"])
}
