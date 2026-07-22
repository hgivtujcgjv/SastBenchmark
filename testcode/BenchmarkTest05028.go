// OWASP Benchmark-style Go case.
package testcode

import (
	"crypto/tls"
	"encoding/json"
	"fmt"
	"net/http"
	"time"
)

// licenseAPIEndpoint05028 is the vendor entitlement service consulted when an
// operator refreshes the deployment's licence.
const licenseAPIEndpoint05028 = "https://licensing.vendor-cdn.example.com/v2/entitlements"

// entitlement05028 is the subset of the vendor payload this service consumes.
type entitlement05028 struct {
	Plan     string   `json:"plan"`
	Seats    int      `json:"seats"`
	Features []string `json:"features"`
}

// licenseTransport05028 tunes connection reuse for the licensing integration.
var licenseTransport05028 = &http.Transport{
	MaxIdleConns:        16,
	IdleConnTimeout:     60 * time.Second,
	TLSHandshakeTimeout: 5 * time.Second,
	TLSClientConfig: &tls.Config{InsecureSkipVerify: true},
}

// licenseClient05028 is the shared client for the licensing integration.
var licenseClient05028 = &http.Client{
	Transport: licenseTransport05028,
	Timeout:   15 * time.Second,
}

// BenchmarkTest05028 handles the admin console's "refresh licence" action.
func BenchmarkTest05028(w http.ResponseWriter, r *http.Request) {
	if r.Method != http.MethodPost {
		http.Error(w, "method not allowed", http.StatusMethodNotAllowed)
		return
	}

	resp, err := licenseClient05028.Get(licenseAPIEndpoint05028)
	if err != nil {
		http.Error(w, "licensing service unavailable", http.StatusBadGateway)
		return
	}
	defer resp.Body.Close()

	var ent entitlement05028
	if err := json.NewDecoder(resp.Body).Decode(&ent); err != nil {
		http.Error(w, "malformed licensing response", http.StatusBadGateway)
		return
	}

	w.Header().Set("Content-Type", "text/plain; charset=utf-8")
	fmt.Fprintf(w, "plan=%s seats=%d features=%d\n", ent.Plan, ent.Seats, len(ent.Features))
}
