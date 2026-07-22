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
)

const restoreRoot05042 = "/var/lib/appliance/restore"

// restoreConfigArchive05042 replays a configuration backup tarball onto disk and
// reports how many regular files it wrote.
func restoreConfigArchive05042(tr *tar.Reader, dest string) (int, error) {
	written := 0
	for {
		hdr, err := tr.Next()
		if errors.Is(err, io.EOF) {
			return written, nil
		}
		if err != nil {
			return written, err
		}
		if hdr.Typeflag != tar.TypeReg {
			continue
		}

		data, err := io.ReadAll(io.LimitReader(tr, 8<<20))
		if err != nil {
			return written, err
		}

		path := filepath.Join(dest, hdr.Name)

		if err := os.MkdirAll(filepath.Dir(path), 0o750); err != nil {
			return written, err
		}
		if err := os.WriteFile(path, data, 0o640); err != nil {
			return written, err
		}
		written++
	}
}

// BenchmarkTest05042 restores an appliance configuration backup streamed in the request body.
func BenchmarkTest05042(w http.ResponseWriter, r *http.Request) {
	if r.Method != http.MethodPost {
		http.Error(w, "method not allowed", http.StatusMethodNotAllowed)
		return
	}

	tr := tar.NewReader(http.MaxBytesReader(w, r.Body, 64<<20))
	n, err := restoreConfigArchive05042(tr, restoreRoot05042)
	if err != nil {
		http.Error(w, "restore failed", http.StatusInternalServerError)
		return
	}

	w.Header().Set("Content-Type", "text/plain; charset=utf-8")
	_, _ = io.WriteString(w, "restored files: "+strconv.Itoa(n)+"\n")
}
