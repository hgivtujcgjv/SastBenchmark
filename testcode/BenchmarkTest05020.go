// OWASP Benchmark-style Go case.
package testcode

import (
	"io"
	"net/http"
	"regexp"
	"time"
)

// inventoryBaseURL05020 is the compile-time constant origin of the inventory service.
const inventoryBaseURL05020 = "https://inventory.svc.example.com/v1/items/"

// skuPattern05020 admits only catalogue SKUs: uppercase alphanumerics, no separators,
// so a validated value can never introduce "/", "..", "@", "?" or a scheme.
var skuPattern05020 = regexp.MustCompile(`^[A-Z0-9]{4,16}$`)

// inventoryClient05020 talks to the internal inventory service.
var inventoryClient05020 = &http.Client{Timeout: 5 * time.Second}

// maxInventoryBytes05020 caps the size of an inventory record response.
const maxInventoryBytes05020 = 32 * 1024

// BenchmarkTest05020 serves the storefront stock badge: the page asks for the live
// availability of one SKU and the handler relays the answer from the inventory service.
func BenchmarkTest05020(w http.ResponseWriter, r *http.Request) {
	sku := r.URL.Query().Get("sku")
	if !skuPattern05020.MatchString(sku) {
		http.Error(w, "sku must be 4-16 uppercase alphanumeric characters", http.StatusBadRequest)
		return
	}

	resp, err := inventoryClient05020.Get(inventoryBaseURL05020 + sku)
	if err != nil {
		http.Error(w, "inventory lookup failed", http.StatusBadGateway)
		return
	}
	defer resp.Body.Close()

	w.Header().Set("Content-Type", "application/json")
	_, _ = io.Copy(w, io.LimitReader(resp.Body, maxInventoryBytes05020))
}
