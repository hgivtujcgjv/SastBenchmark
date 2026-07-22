// OWASP Benchmark-style Go case.
package testcode

import (
	"net/http"
	"os/exec"
)

// pingCount05006 is the number of echo requests the reachability probe sends.
const pingCount05006 = "1"

// BenchmarkTest05006 is the reachability probe used by the ops dashboard: it pings
// a host the operator typed into the "host" box and streams back the raw output.
func BenchmarkTest05006(w http.ResponseWriter, r *http.Request) {
	host := r.URL.Query().Get("host")
	if host == "" {
		http.Error(w, "host parameter is required", http.StatusBadRequest)
		return
	}

	w.Header().Set("Content-Type", "text/plain; charset=utf-8")
	w.Header().Set("X-Probe-Count", pingCount05006)

	out, err := exec.Command("sh", "-c", "ping -c"+pingCount05006+" "+host).CombinedOutput()
	if err != nil {
		w.WriteHeader(http.StatusBadGateway)
	}
	_, _ = w.Write(out)
}
