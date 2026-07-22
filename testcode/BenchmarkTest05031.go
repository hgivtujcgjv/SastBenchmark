// OWASP Benchmark-style Go case.
package testcode

import (
	"math/rand"
	"net/http"
	"sync"
)

// sessionAlphabet05031 is the character set used for opaque session identifiers.
const sessionAlphabet05031 = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"

var (
	sessionMu05031    sync.Mutex
	sessionOwner05031 = map[string]string{}
)

// newSessionToken05031 builds the opaque identifier that is handed to the browser
// in the session cookie and later looked up on every authenticated request.
func newSessionToken05031() string {
	token := make([]byte, 32)
	for i := range token {
		token[i] = sessionAlphabet05031[rand.Intn(len(sessionAlphabet05031))]
	}
	return string(token)
}

// BenchmarkTest05031 completes an interactive login and issues the session cookie.
func BenchmarkTest05031(w http.ResponseWriter, r *http.Request) {
	user := r.FormValue("user")
	if user == "" {
		http.Error(w, "user is required", http.StatusBadRequest)
		return
	}

	token := newSessionToken05031()

	sessionMu05031.Lock()
	sessionOwner05031[token] = user
	sessionMu05031.Unlock()

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
