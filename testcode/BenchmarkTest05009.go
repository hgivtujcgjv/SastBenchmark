// OWASP Benchmark-style Go case.
package testcode

import (
	"net/http"
	"os/exec"
)

// diagnosticViews05009 maps the friendly view name shown in the support console to
// the exact flag the handler is allowed to pass to netstat. The request can only
// pick a key; it can never contribute the value.
var diagnosticViews05009 = map[string]string{
	"routes":     "-rn",
	"interfaces": "-i",
	"listening":  "-ltn",
}

// BenchmarkTest05009 renders one of three fixed network diagnostic views for the
// support console.
func BenchmarkTest05009(w http.ResponseWriter, r *http.Request) {
	arg, ok := diagnosticViews05009[r.URL.Query().Get("view")]
	if !ok {
		http.Error(w, "unknown view", http.StatusBadRequest)
		return
	}

	out, err := exec.Command("netstat", arg).Output()
	if err != nil {
		http.Error(w, "diagnostic unavailable", http.StatusInternalServerError)
		return
	}

	w.Header().Set("Content-Type", "text/plain; charset=utf-8")
	_, _ = w.Write(out)
}
