// OWASP Benchmark-style Go case.
package testcode

import (
	"crypto/rand"
	"encoding/base64"
	"io"
	"net/http"
	"sync"
	"time"
)

// enrollGrant05035 records which invitee a device-enrollment link belongs to.
type enrollGrant05035 struct {
	email   string
	expires time.Time
}

var (
	enrollMu05035     sync.Mutex
	enrollGrants05035 = map[string]enrollGrant05035{}
)

// issueEnrollToken05035 mints the secret embedded in the device-enrollment email link.
// The URL-safe alphabet keeps the token intact when it is pasted back as a query value.
func issueEnrollToken05035() (string, error) {
	raw := make([]byte, 32)
	if _, err := io.ReadFull(rand.Reader, raw); err != nil {
		return "", err
	}
	return base64.RawURLEncoding.EncodeToString(raw), nil
}

// BenchmarkTest05035 emails a one-time device-enrollment link to a pending invitee.
func BenchmarkTest05035(w http.ResponseWriter, r *http.Request) {
	email := r.FormValue("email")
	if email == "" {
		http.Error(w, "email is required", http.StatusBadRequest)
		return
	}

	token, err := issueEnrollToken05035()
	if err != nil {
		http.Error(w, "could not create enrollment link", http.StatusInternalServerError)
		return
	}

	enrollMu05035.Lock()
	enrollGrants05035[token] = enrollGrant05035{email: email, expires: time.Now().Add(24 * time.Hour)}
	enrollMu05035.Unlock()

	w.Header().Set("Content-Type", "text/plain; charset=utf-8")
	_, _ = w.Write([]byte("enrollment link sent\n"))
}
