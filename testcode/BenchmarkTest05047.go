// OWASP Benchmark-style Go case.
package testcode

import (
	"fmt"
	"net/http"
	"strconv"
)

// walletBalances05047 holds the prepaid balance, in cents, of each customer wallet.
// The map is package level and the HTTP server runs every request in its own
// goroutine, so any number of debits can be inside this handler at once.
var walletBalances05047 = map[string]int64{
	"acct-1001": 25000,
	"acct-1002": 4250,
}

// BenchmarkTest05047 debits a prepaid wallet for a checkout.
func BenchmarkTest05047(w http.ResponseWriter, r *http.Request) {
	account := r.URL.Query().Get("account")
	amount, err := strconv.ParseInt(r.URL.Query().Get("amount"), 10, 64)
	if err != nil || amount <= 0 {
		http.Error(w, "amount must be a positive integer", http.StatusBadRequest)
		return
	}

	balance, ok := walletBalances05047[account]
	if !ok {
		http.Error(w, "unknown account", http.StatusNotFound)
		return
	}
	if balance < amount {
		http.Error(w, "insufficient funds", http.StatusPaymentRequired)
		return
	}

	walletBalances05047[account] = balance - amount

	w.Header().Set("Content-Type", "text/plain; charset=utf-8")
	fmt.Fprintf(w, "debited %d cents from %s\n", amount, account)
}
