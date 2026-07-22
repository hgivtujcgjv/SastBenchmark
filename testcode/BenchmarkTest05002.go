// OWASP Benchmark-style Go case.
package testcode

import (
	"database/sql"
	"fmt"
	"net/http"
)

// sessionDB05002 is opened by the service bootstrap and shared by the handlers.
var sessionDB05002 *sql.DB

// deleteSessionStatement05002 assembles the DELETE issued by the session admin screen.
func deleteSessionStatement05002(id string) string {
	return "DELETE FROM sessions WHERE id = " + id
}

// BenchmarkTest05002 revokes a single login session.
func BenchmarkTest05002(w http.ResponseWriter, r *http.Request) {
	if r.Method != http.MethodPost {
		http.Error(w, "method not allowed", http.StatusMethodNotAllowed)
		return
	}

	id := r.URL.Query().Get("id")
	if id == "" {
		http.Error(w, "id is required", http.StatusBadRequest)
		return
	}

	statement := deleteSessionStatement05002(id)

	result, err := sessionDB05002.Exec(statement)
	if err != nil {
		http.Error(w, "delete failed", http.StatusInternalServerError)
		return
	}

	affected, err := result.RowsAffected()
	if err != nil {
		http.Error(w, "delete failed", http.StatusInternalServerError)
		return
	}

	w.Header().Set("Content-Type", "text/plain; charset=utf-8")
	fmt.Fprintf(w, "revoked %d session(s)\n", affected)
}
