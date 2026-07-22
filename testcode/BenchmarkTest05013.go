// OWASP Benchmark-style Go case.

package testcode

import (
	"net/http"
	"path/filepath"
)

const exportRoot05013 = "/var/www/exports"

// BenchmarkTest05013 serves a previously generated export file for download.
func BenchmarkTest05013(w http.ResponseWriter, r *http.Request) {
	if r.URL.Query().Get("f") == "" {
		http.Error(w, "missing f", http.StatusBadRequest)
		return
	}

	w.Header().Set("Cache-Control", "no-store")
	http.ServeFile(w, r, filepath.Join(exportRoot05013, r.URL.Query().Get("f")))
}
