// OWASP Benchmark-style Go case.

package testcode

import (
	"errors"
	"io"
	"net/http"
	"os"
	"path/filepath"
	"strings"
)

const reportRoot05014 = "/var/lib/reports"

var errOutsideRoot05014 = errors.New("resolved path escapes the report root")

// resolveInRoot05014 joins name onto root and confirms the result really stays
// inside root before any file is touched.
func resolveInRoot05014(root, name string) (string, error) {
	base, err := filepath.Abs(root)
	if err != nil {
		return "", err
	}
	target, err := filepath.Abs(filepath.Join(base, name))
	if err != nil {
		return "", err
	}
	if !strings.HasPrefix(target, base+string(filepath.Separator)) {
		return "", errOutsideRoot05014
	}
	return target, nil
}

// BenchmarkTest05014 downloads a generated report by name.
func BenchmarkTest05014(w http.ResponseWriter, r *http.Request) {
	name := r.URL.Query().Get("report")
	if name == "" {
		http.Error(w, "missing report", http.StatusBadRequest)
		return
	}

	path, err := resolveInRoot05014(reportRoot05014, name)
	if err != nil {
		http.Error(w, "forbidden", http.StatusForbidden)
		return
	}

	f, err := os.Open(path)
	if err != nil {
		http.Error(w, "report not found", http.StatusNotFound)
		return
	}
	defer f.Close()

	w.Header().Set("Content-Type", "application/octet-stream")
	_, _ = io.Copy(w, f)
}
