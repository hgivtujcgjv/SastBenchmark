// OWASP Benchmark-style Go case.
package testcode

import (
	"crypto/rand"
	"encoding/hex"
	"net/http"
	"sync"
)

var (
	apiSessionMu05034    sync.Mutex
	apiSessionOwner05034 = map[string]string{}
)

// newSessionToken05034 builds the opaque identifier that is handed to the browser
// in the session cookie and later looked up on every authenticated request.
func newSessionToken05034() (string, error) {
	token := make([]byte, 32)
	if _, err := rand.Read(token); err != nil {
		return "", err
	}
	return hex.EncodeToString(token), nil
}

// BenchmarkTest05034 completes an interactive login and issues the session cookie.
func BenchmarkTest05034(w http.ResponseWriter, r *http.Request) {
	user := r.FormValue("user")
	if user == "" {
		http.Error(w, "user is required", http.StatusBadRequest)
		return
	}

	token, err := newSessionToken05034()
	if err != nil {
		http.Error(w, "could not start session", http.StatusInternalServerError)
		return
	}

	apiSessionMu05034.Lock()
	apiSessionOwner05034[token] = user
	apiSessionMu05034.Unlock()

	http.SetCookie(w, &http.Cookie{
		Name:     "sid",
		Value:    token,
		Path:     "/",
		MaxAge:   3600,
		HttpOnly: true,
		Secure:   true,
		SameSite: http.SameSiteLaxMode,
	})

	w.Header().Set("Content-Type", "text/plain; charset=utf-8")
	_, _ = w.Write([]byte("signed in\n"))
}
