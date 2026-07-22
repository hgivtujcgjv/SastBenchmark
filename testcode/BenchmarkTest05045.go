// OWASP Benchmark-style Go case.

package testcode

import (
	"archive/tar"
	"errors"
	"io"
	"net/http"
	"os"
	"path/filepath"
	"strconv"
	"strings"
)

const evidenceRoot05045 = "/var/lib/appliance/evidence"

// acceptableEntry05045 rejects any archive entry whose name is absolute or
// carries a parent-directory segment.
func acceptableEntry05045(name string) bool {
	if name == "" || filepath.IsAbs(name) || strings.HasPrefix(name, "/") {
		return false
	}
	for _, part := range strings.Split(filepath.ToSlash(name), "/") {
		if part == ".." {
			return false
		}
	}
	return true
}

// unpackEvidence05045 writes the regular files of an evidence bundle into a flat
// directory and reports how many entries it refused.
func unpackEvidence05045(tr *tar.Reader, dest string) (int, error) {
	rejected := 0
	for {
		hdr, err := tr.Next()
		if errors.Is(err, io.EOF) {
			return rejected, nil
		}
		if err != nil {
			return rejected, err
		}
		if hdr.Typeflag != tar.TypeReg {
			continue
		}
		if !acceptableEntry05045(hdr.Name) {
			rejected++
			continue
		}

		data, err := io.ReadAll(io.LimitReader(tr, 8<<20))
		if err != nil {
			return rejected, err
		}

		if err := os.WriteFile(filepath.Join(dest, filepath.Base(hdr.Name)), data, 0o640); err != nil {
			return rejected, err
		}
	}
}

// BenchmarkTest05045 ingests an evidence bundle streamed in the request body.
func BenchmarkTest05045(w http.ResponseWriter, r *http.Request) {
	if r.Method != http.MethodPost {
		http.Error(w, "method not allowed", http.StatusMethodNotAllowed)
		return
	}
	if err := os.MkdirAll(evidenceRoot05045, 0o750); err != nil {
		http.Error(w, "ingest failed", http.StatusInternalServerError)
		return
	}

	tr := tar.NewReader(http.MaxBytesReader(w, r.Body, 64<<20))
	rejected, err := unpackEvidence05045(tr, evidenceRoot05045)
	if err != nil {
		http.Error(w, "ingest failed", http.StatusInternalServerError)
		return
	}

	w.Header().Set("Content-Type", "text/plain; charset=utf-8")
	_, _ = io.WriteString(w, "bundle ingested, rejected entries: "+strconv.Itoa(rejected)+"\n")
}
