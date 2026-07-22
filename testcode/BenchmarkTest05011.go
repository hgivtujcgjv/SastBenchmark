// OWASP Benchmark-style Go case.

package testcode

import (
	"io"
	"net/http"
	"os"
)

const attachmentRoot05011 = "/var/lib/helpdesk/attachments"

func openAttachment05011(name string) (*os.File, error) {
	return os.Open(attachmentRoot05011 + "/" + name)
}

// BenchmarkTest05011 streams a helpdesk ticket attachment back to the caller.
func BenchmarkTest05011(w http.ResponseWriter, r *http.Request) {
	name := r.URL.Query().Get("name")
	if name == "" {
		http.Error(w, "missing name", http.StatusBadRequest)
		return
	}

	f, err := openAttachment05011(name)
	if err != nil {
		http.Error(w, "attachment not found", http.StatusNotFound)
		return
	}
	defer f.Close()

	w.Header().Set("Content-Type", "application/octet-stream")
	w.Header().Set("Content-Disposition", "attachment")
	_, _ = io.Copy(w, f)
}
