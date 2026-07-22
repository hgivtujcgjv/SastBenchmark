// OWASP Benchmark-style Go case.
package testcode

import (
	"crypto/tls"
	"encoding/json"
	"fmt"
	"net/http"
	"time"
)

// billingAPIEndpoint05029 is the payment provider's invoice service consulted
// when an operator refreshes the deployment's billing summary.
const billingAPIEndpoint05029 = "https://billing.provider-cdn.example.com/v2/invoices/current"

// invoiceSummary05029 is the subset of the provider payload this service consumes.
type invoiceSummary05029 struct {
	Number    string `json:"number"`
	AmountDue int64  `json:"amount_due_cents"`
	Currency  string `json:"currency"`
}

// billingTransport05029 tunes connection reuse for the billing integration. Only
// the minimum protocol version is pinned; every other TLS knob keeps the
// crypto/tls default.
var billingTransport05029 = &http.Transport{
	MaxIdleConns:        16,
	IdleConnTimeout:     60 * time.Second,
	TLSHandshakeTimeout: 5 * time.Second,
	TLSClientConfig: &tls.Config{MinVersion: tls.VersionTLS12},
}

// billingClient05029 is the shared client for the billing integration.
var billingClient05029 = &http.Client{
	Transport: billingTransport05029,
	Timeout:   15 * time.Second,
}

// BenchmarkTest05029 handles the admin console's "refresh billing" action.
func BenchmarkTest05029(w http.ResponseWriter, r *http.Request) {
	if r.Method != http.MethodPost {
		http.Error(w, "method not allowed", http.StatusMethodNotAllowed)
		return
	}

	resp, err := billingClient05029.Get(billingAPIEndpoint05029)
	if err != nil {
		http.Error(w, "billing service unavailable", http.StatusBadGateway)
		return
	}
	defer resp.Body.Close()

	var inv invoiceSummary05029
	if err := json.NewDecoder(resp.Body).Decode(&inv); err != nil {
		http.Error(w, "malformed billing response", http.StatusBadGateway)
		return
	}

	w.Header().Set("Content-Type", "text/plain; charset=utf-8")
	fmt.Fprintf(w, "invoice=%s due=%d %s\n", inv.Number, inv.AmountDue, inv.Currency)
}
