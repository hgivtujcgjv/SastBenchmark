// OWASP Benchmark-style Go case.

package testcode

import (
	"net/http"
	"strings"
)

// BenchmarkTest05023 returns the live preview fragment for a comment being composed.
func BenchmarkTest05023(w http.ResponseWriter, r *http.Request) {
	if err := r.ParseForm(); err != nil {
		http.Error(w, "bad request", http.StatusBadRequest)
		return
	}

	body := strings.TrimSpace(r.PostFormValue("body"))
	if body == "" {
		http.Error(w, "empty comment", http.StatusBadRequest)
		return
	}

	w.Header().Set("Content-Type", "text/html; charset=utf-8")
	_, _ = w.Write([]byte("<p class=\"comment-preview\">" + body + "</p>"))
}
