// OWASP Benchmark-style Go case.
package testcode

import (
	"crypto/tls"
	"fmt"
	"net/http"
)

// metricsCollectorAddr05026 is the TLS listener of the internal metrics collector
// that every application node ships its counters to.
const metricsCollectorAddr05026 = "metrics-collector.internal.acme-corp.net:8443"

// BenchmarkTest05026 backs the /healthz/metrics-link readiness probe. The
// orchestrator polls it before routing traffic to a node, so the handler opens a
// TLS connection to the collector and reports what was negotiated.
func BenchmarkTest05026(w http.ResponseWriter, r *http.Request) {
	if r.Method != http.MethodGet {
		http.Error(w, "method not allowed", http.StatusMethodNotAllowed)
		return
	}

	conn, err := tls.Dial("tcp", metricsCollectorAddr05026, &tls.Config{InsecureSkipVerify: true})
	if err != nil {
		http.Error(w, "metrics collector unreachable", http.StatusBadGateway)
		return
	}
	defer conn.Close()

	state := conn.ConnectionState()
	w.Header().Set("Content-Type", "text/plain; charset=utf-8")
	fmt.Fprintf(w, "collector: %s\nprotocol: %s\ncipher: %s\n",
		metricsCollectorAddr05026,
		tls.VersionName(state.Version),
		tls.CipherSuiteName(state.CipherSuite))
}
