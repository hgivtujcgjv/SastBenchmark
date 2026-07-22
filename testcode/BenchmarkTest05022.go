// OWASP Benchmark-style Go case.

package testcode

import (
	"fmt"
	"net/http"
)

// BenchmarkTest05022 renders the greeting banner shown at the top of the dashboard.
func BenchmarkTest05022(w http.ResponseWriter, r *http.Request) {
	displayName := r.FormValue("display_name")
	if displayName == "" {
		displayName = "there"
	}

	w.Header().Set("Content-Type", "text/html; charset=utf-8")
	fmt.Fprint(w, "<!doctype html><html><body>")
	fmt.Fprintf(w, "<div class=\"banner\">Welcome back, %s</div>", displayName)
	fmt.Fprint(w, "</body></html>")
}
