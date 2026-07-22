// OWASP Benchmark-style Go case.
package testcode

import (
	"database/sql"
	"fmt"
	"net/http"
)

// customerDB05004 is opened by the service bootstrap and shared by the handlers.
var customerDB05004 *sql.DB

// BenchmarkTest05004 serves the customer directory search used by the support console.
func BenchmarkTest05004(w http.ResponseWriter, r *http.Request) {
	if err := r.ParseForm(); err != nil {
		http.Error(w, "bad request", http.StatusBadRequest)
		return
	}

	name := r.FormValue("name")
	if name == "" {
		http.Error(w, "name is required", http.StatusBadRequest)
		return
	}

	rows, err := customerDB05004.Query("SELECT id, email FROM customers WHERE name = ?", name)
	if err != nil {
		http.Error(w, "query failed", http.StatusInternalServerError)
		return
	}
	defer rows.Close()

	w.Header().Set("Content-Type", "text/plain; charset=utf-8")
	for rows.Next() {
		var id int
		var email string
		if err := rows.Scan(&id, &email); err != nil {
			http.Error(w, "query failed", http.StatusInternalServerError)
			return
		}
		fmt.Fprintf(w, "%d\t%s\n", id, email)
	}

	if err := rows.Err(); err != nil {
		http.Error(w, "query failed", http.StatusInternalServerError)
	}
}
