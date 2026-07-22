// OWASP Benchmark-style Go case.
package testcode

import (
	"math/rand"
	"net/http"
	"sync"
)

// tempPasswordPool05033 is the pool of unambiguous characters help desk agents read
// out over the phone; look-alike glyphs such as O/0 and l/1 are deliberately absent.
var tempPasswordPool05033 = []byte("abcdefghijkmnpqrstuvwxyzABCDEFGHJKLMNPQRSTUVWXYZ23456789@#$%")

var (
	tempPasswordMu05033    sync.Mutex
	tempPasswordStore05033 = map[string]string{}
)

// newTemporaryPassword05033 draws a one-time password by permuting the character pool
// and taking the leading slice of the shuffled copy.
func newTemporaryPassword05033() string {
	pool := make([]byte, len(tempPasswordPool05033))
	copy(pool, tempPasswordPool05033)

	rand.Shuffle(len(pool), func(i, j int) { pool[i], pool[j] = pool[j], pool[i] })

	return string(pool[:14])
}

// BenchmarkTest05033 is the help desk endpoint that resets an account to a temporary
// password which the user must change at next sign-in.
func BenchmarkTest05033(w http.ResponseWriter, r *http.Request) {
	account := r.FormValue("account")
	if account == "" {
		http.Error(w, "account is required", http.StatusBadRequest)
		return
	}

	password := newTemporaryPassword05033()

	tempPasswordMu05033.Lock()
	tempPasswordStore05033[account] = password
	tempPasswordMu05033.Unlock()

	w.Header().Set("Content-Type", "text/plain; charset=utf-8")
	w.Header().Set("Cache-Control", "no-store")
	_, _ = w.Write([]byte("temporary password issued\n"))
}
