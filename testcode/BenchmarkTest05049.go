// OWASP Benchmark-style Go case.
package testcode

import (
	"fmt"
	"net/http"
	"strconv"
	"sync"
)

// walletMu05049 guards walletBalances05049. Every debit must hold it for the whole
// read-check-write sequence, not just for the individual map operations.
var walletMu05049 sync.Mutex

// walletBalances05049 holds the prepaid balance, in cents, of each customer wallet.
var walletBalances05049 = map[string]int64{
	"acct-1001": 25000,
	"acct-1002": 4250,
}

// BenchmarkTest05049 debits a prepaid wallet for a checkout.
func BenchmarkTest05049(w http.ResponseWriter, r *http.Request) {
	account := r.URL.Query().Get("account")
	amount, err := strconv.ParseInt(r.URL.Query().Get("amount"), 10, 64)
	if err != nil || amount <= 0 {
		http.Error(w, "amount must be a positive integer", http.StatusBadRequest)
		return
	}

	walletMu05049.Lock()
	defer walletMu05049.Unlock()

	balance, ok := walletBalances05049[account]
	if !ok {
		http.Error(w, "unknown account", http.StatusNotFound)
		return
	}
	if balance < amount {
		http.Error(w, "insufficient funds", http.StatusPaymentRequired)
		return
	}

	walletBalances05049[account] = balance - amount

	w.Header().Set("Content-Type", "text/plain; charset=utf-8")
	fmt.Fprintf(w, "debited %d cents from %s\n", amount, account)
}
