// OWASP Benchmark-style Go case.

package testcode

import (
	"archive/zip"
	"bytes"
	"errors"
	"io"
	"net/http"
	"os"
	"path/filepath"
	"strconv"
	"strings"
)

const skinRoot05044 = "/var/lib/portal/skins"

var errEscapesDest05044 = errors.New("archive entry escapes the destination directory")

// confineToDest05044 joins an archive entry name onto dest and proves the
// resolved path is still under dest before it is handed to any file API.
func confineToDest05044(dest, name string) (string, error) {
	base, err := filepath.Abs(dest)
	if err != nil {
		return "", err
	}
	target, err := filepath.Abs(filepath.Join(base, name))
	if err != nil {
		return "", err
	}
	if !strings.HasPrefix(target, base+string(filepath.Separator)) {
		return "", errEscapesDest05044
	}
	return target, nil
}

// extractSkin05044 unpacks a skin package, skipping entries that would land
// outside the skin root, and reports how many it refused.
func extractSkin05044(zr *zip.Reader, dest string) (int, error) {
	skipped := 0
	for _, f := range zr.File {
		if f.FileInfo().IsDir() {
			continue
		}

		target, err := confineToDest05044(dest, f.Name)
		if err != nil {
			skipped++
			continue
		}

		if err := os.MkdirAll(filepath.Dir(target), 0o750); err != nil {
			return skipped, err
		}
		src, err := f.Open()
		if err != nil {
			return skipped, err
		}
		out, err := os.OpenFile(target, os.O_WRONLY|os.O_CREATE|os.O_TRUNC, 0o640)
		if err != nil {
			src.Close()
			return skipped, err
		}
		_, err = io.Copy(out, src)
		out.Close()
		src.Close()
		if err != nil {
			return skipped, err
		}
	}
	return skipped, nil
}

// BenchmarkTest05044 installs a portal skin package uploaded in the request body.
func BenchmarkTest05044(w http.ResponseWriter, r *http.Request) {
	body, err := io.ReadAll(http.MaxBytesReader(w, r.Body, 32<<20))
	if err != nil {
		http.Error(w, "upload too large", http.StatusBadRequest)
		return
	}

	zr, err := zip.NewReader(bytes.NewReader(body), int64(len(body)))
	if err != nil {
		http.Error(w, "not a zip archive", http.StatusBadRequest)
		return
	}

	skipped, err := extractSkin05044(zr, skinRoot05044)
	if err != nil {
		http.Error(w, "install failed", http.StatusInternalServerError)
		return
	}

	w.Header().Set("Content-Type", "text/plain; charset=utf-8")
	_, _ = io.WriteString(w, "skin installed, rejected entries: "+strconv.Itoa(skipped)+"\n")
}
