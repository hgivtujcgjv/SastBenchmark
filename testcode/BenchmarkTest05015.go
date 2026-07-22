// OWASP Benchmark-style Go case.

package testcode

import (
	"net/http"
	"os"
	"path/filepath"
)

const avatarRoot05015 = "/var/lib/profiles/avatars"

// BenchmarkTest05015 returns a stored profile avatar image.
func BenchmarkTest05015(w http.ResponseWriter, r *http.Request) {
	name := r.URL.Query().Get("avatar")
	if name == "" {
		http.Error(w, "missing avatar", http.StatusBadRequest)
		return
	}

	data, err := os.ReadFile(filepath.Join(avatarRoot05015, filepath.Base(name)))
	if err != nil {
		http.Error(w, "avatar not found", http.StatusNotFound)
		return
	}

	w.Header().Set("Content-Type", http.DetectContentType(data))
	w.Header().Set("Cache-Control", "private, max-age=60")
	_, _ = w.Write(data)
}
