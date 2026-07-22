// OWASP Benchmark-style Go case.
package testcode

import (
	"fmt"
	"net/http"
	"time"
)

// webhookProbeClient05017 is the shared client used by every outbound integration probe.
var webhookProbeClient05017 = &http.Client{Timeout: 10 * time.Second}

// webhookProbeAgent05017 identifies the probe to the customer's endpoint.
const webhookProbeAgent05017 = "acme-webhook-probe/1.4"

// BenchmarkTest05017 implements the "test this endpoint" button in the integrations
// settings screen: the customer enters the URL that should receive their webhooks and
// the platform performs a reachability probe against it, echoing back the status.
func BenchmarkTest05017(w http.ResponseWriter, r *http.Request) {
	endpoint := r.URL.Query().Get("endpoint")
	if endpoint == "" {
		http.Error(w, "endpoint parameter is required", http.StatusBadRequest)
		return
	}

	req, err := http.NewRequest("GET", endpoint, nil)
	if err != nil {
		http.Error(w, "endpoint is not a valid URL", http.StatusBadRequest)
		return
	}
	req.Header.Set("User-Agent", webhookProbeAgent05017)
	req.Header.Set("Accept", "*/*")

	resp, err := webhookProbeClient05017.Do(req)
	if err != nil {
		http.Error(w, "probe failed", http.StatusBadGateway)
		return
	}
	defer resp.Body.Close()

	w.Header().Set("Content-Type", "text/plain; charset=utf-8")
	fmt.Fprintf(w, "probe status: %d\ncontent-type: %s\n", resp.StatusCode, resp.Header.Get("Content-Type"))
}
