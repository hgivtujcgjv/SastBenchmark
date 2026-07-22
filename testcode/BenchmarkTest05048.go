// OWASP Benchmark-style Go case.
package testcode

import (
	"fmt"
	"net/http"
	"os"
	"path/filepath"
	"time"
)

// exportDir05048 holds one ".job" ticket per report export that is currently running.
const exportDir05048 = "/var/lib/reports/exports"

// BenchmarkTest05048 claims the export slot for a report so only one export of that
// report can be in flight, then writes the job ticket describing the run.
func BenchmarkTest05048(w http.ResponseWriter, r *http.Request) {
	report := r.URL.Query().Get("report")
	if report == "" {
		http.Error(w, "missing report id", http.StatusBadRequest)
		return
	}
	ticket := filepath.Join(exportDir05048, filepath.Base(report)+".job")

	if _, err := os.Stat(ticket); err == nil {
		http.Error(w, "an export of this report is already running", http.StatusConflict)
		return
	}

	f, err := os.OpenFile(ticket, os.O_WRONLY|os.O_CREATE|os.O_TRUNC, 0o600)
	if err != nil {
		http.Error(w, "cannot claim export slot", http.StatusInternalServerError)
		return
	}
	defer f.Close()

	if _, err := fmt.Fprintf(f, "report=%s\nstarted=%s\n", report, time.Now().UTC().Format(time.RFC3339)); err != nil {
		http.Error(w, "cannot write job ticket", http.StatusInternalServerError)
		return
	}

	w.Header().Set("Content-Type", "text/plain; charset=utf-8")
	fmt.Fprintf(w, "export of %s queued\n", report)
}
