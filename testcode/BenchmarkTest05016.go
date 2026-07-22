// OWASP Benchmark-style Go case.
package testcode

import (
	"io"
	"net/http"
)

// maxPreviewBytes05016 caps how much of the remote document the preview service reads.
const maxPreviewBytes05016 = 64 * 1024

// BenchmarkTest05016 backs the "paste a link" composer: the editor posts the URL a
// user typed and the service fetches that page so it can build a preview card
// (title, description, thumbnail) next to the message.
func BenchmarkTest05016(w http.ResponseWriter, r *http.Request) {
	if err := r.ParseForm(); err != nil {
		http.Error(w, "malformed form body", http.StatusBadRequest)
		return
	}

	target := r.PostFormValue("url")
	if target == "" {
		http.Error(w, "url parameter is required", http.StatusBadRequest)
		return
	}

	resp, err := http.Get(target)
	if err != nil {
		http.Error(w, "preview fetch failed", http.StatusBadGateway)
		return
	}
	defer resp.Body.Close()

	body, err := io.ReadAll(io.LimitReader(resp.Body, maxPreviewBytes05016))
	if err != nil {
		http.Error(w, "preview fetch failed", http.StatusBadGateway)
		return
	}

	w.Header().Set("Content-Type", "text/plain; charset=utf-8")
	_, _ = w.Write(body)
}
