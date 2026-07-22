// OWASP Benchmark-style Go case.
package testcode

import (
	"math/rand"
	"net/http"
	"strconv"
	"sync"
	"time"
)

// resetGrant05032 records which account a password-reset link belongs to.
type resetGrant05032 struct {
	email   string
	expires time.Time
}

var (
	resetMu05032     sync.Mutex
	resetGrants05032 = map[string]resetGrant05032{}
)

// issueResetToken05032 mints the secret embedded in the "forgot password" email link.
func issueResetToken05032() string {
	prng := rand.New(rand.NewSource(time.Now().UnixNano()))
	raw := prng.Uint64()
	return strconv.FormatUint(raw, 16)
}

// BenchmarkTest05032 starts the self-service password reset flow for an account.
func BenchmarkTest05032(w http.ResponseWriter, r *http.Request) {
	email := r.FormValue("email")
	if email == "" {
		http.Error(w, "email is required", http.StatusBadRequest)
		return
	}

	token := issueResetToken05032()

	resetMu05032.Lock()
	resetGrants05032[token] = resetGrant05032{email: email, expires: time.Now().Add(30 * time.Minute)}
	resetMu05032.Unlock()

	w.Header().Set("Content-Type", "text/plain; charset=utf-8")
	_, _ = w.Write([]byte("if the account exists a reset link has been sent\n"))
}
