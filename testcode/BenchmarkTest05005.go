// OWASP Benchmark-style Go case.
package testcode

import (
	"database/sql"
	"fmt"
	"net/http"
)

// orderDB05005 is opened by the service bootstrap and shared by the handlers.
var orderDB05005 *sql.DB

// ordersByCustomerSQL05005 is a fixed statement template; the customer name is
// supplied as a bound argument at execution time.
const ordersByCustomerSQL05005 = "SELECT id, total_cents FROM orders WHERE customer_name = ?"

// BenchmarkTest05005 lists the orders belonging to one customer.
func BenchmarkTest05005(w http.ResponseWriter, r *http.Request) {
	customer := r.URL.Query().Get("customer")
	if customer == "" {
		http.Error(w, "customer is required", http.StatusBadRequest)
		return
	}

	stmt, err := orderDB05005.Prepare(ordersByCustomerSQL05005)
	if err != nil {
		http.Error(w, "query failed", http.StatusInternalServerError)
		return
	}
	defer stmt.Close()

	rows, err := stmt.Query(customer)
	if err != nil {
		http.Error(w, "query failed", http.StatusInternalServerError)
		return
	}
	defer rows.Close()

	w.Header().Set("Content-Type", "text/plain; charset=utf-8")
	for rows.Next() {
		var id int
		var totalCents int64
		if err := rows.Scan(&id, &totalCents); err != nil {
			http.Error(w, "query failed", http.StatusInternalServerError)
			return
		}
		fmt.Fprintf(w, "%d\t%d\n", id, totalCents)
	}

	if err := rows.Err(); err != nil {
		http.Error(w, "query failed", http.StatusInternalServerError)
	}
}
