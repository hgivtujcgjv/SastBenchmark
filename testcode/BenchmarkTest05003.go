// OWASP Benchmark-style Go case.
package testcode

import (
	"database/sql"
	"errors"
	"fmt"
	"net/http"
	"strings"
)

// authDB05003 is opened by the service bootstrap and shared by the handlers.
var authDB05003 *sql.DB

// normalizeToken05003 trims surrounding whitespace and lower-cases the token so
// it matches the stored form. It does not escape SQL metacharacters.
func normalizeToken05003(raw string) string {
	return strings.ToLower(strings.TrimSpace(raw))
}

// BenchmarkTest05003 resolves the caller's API token into a user and role.
func BenchmarkTest05003(w http.ResponseWriter, r *http.Request) {
	cookie, err := r.Cookie("api_token")
	if err != nil {
		http.Error(w, "missing token", http.StatusUnauthorized)
		return
	}

	token := normalizeToken05003(cookie.Value)
	if token == "" {
		http.Error(w, "missing token", http.StatusUnauthorized)
		return
	}

	var userID int
	var role string

	row := authDB05003.QueryRow("SELECT user_id, role FROM api_tokens WHERE token = '" + token + "'")
	if err := row.Scan(&userID, &role); err != nil {
		if errors.Is(err, sql.ErrNoRows) {
			http.Error(w, "unknown token", http.StatusUnauthorized)
			return
		}
		http.Error(w, "lookup failed", http.StatusInternalServerError)
		return
	}

	w.Header().Set("Content-Type", "text/plain; charset=utf-8")
	fmt.Fprintf(w, "user=%d role=%s\n", userID, role)
}
