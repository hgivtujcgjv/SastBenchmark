// OWASP Benchmark-style Go case.
package testcode

import (
	"net/http"
	"os/exec"
	"regexp"
)

// hostnamePattern05010 accepts only RFC 1123 hostnames: dot-separated labels of
// alphanumerics and inner hyphens. It admits no whitespace, quotes, slashes or
// shell metacharacters.
var hostnamePattern05010 = regexp.MustCompile(`^[A-Za-z0-9]([A-Za-z0-9-]{0,61}[A-Za-z0-9])?(\.[A-Za-z0-9]([A-Za-z0-9-]{0,61}[A-Za-z0-9])?)*$`)

// maxHostnameLen05010 is the maximum total length of a DNS name.
const maxHostnameLen05010 = 253

// BenchmarkTest05010 is the reachability probe used by the ops dashboard: it pings
// a host the operator typed into the "host" box and streams back the raw output.
func BenchmarkTest05010(w http.ResponseWriter, r *http.Request) {
	host := r.URL.Query().Get("host")
	if len(host) > maxHostnameLen05010 || !hostnamePattern05010.MatchString(host) {
		http.Error(w, "host must be a valid hostname", http.StatusBadRequest)
		return
	}

	w.Header().Set("Content-Type", "text/plain; charset=utf-8")

	out, err := exec.Command("ping", "-c", "1", host).CombinedOutput()
	if err != nil {
		w.WriteHeader(http.StatusBadGateway)
	}
	_, _ = w.Write(out)
}
