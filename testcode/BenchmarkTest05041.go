// OWASP Benchmark-style Go case.

package testcode

import (
	"archive/zip"
	"bytes"
	"io"
	"net/http"
	"os"
	"path/filepath"
)

const themeRoot05041 = "/var/lib/portal/themes"

// extractTheme05041 unpacks an uploaded theme package into the theme root.
func extractTheme05041(zr *zip.Reader, dest string) error {
	for _, f := range zr.File {
		if f.FileInfo().IsDir() {
			continue
		}

		target := filepath.Join(dest, f.Name)

		if err := os.MkdirAll(filepath.Dir(target), 0o750); err != nil {
			return err
		}
		src, err := f.Open()
		if err != nil {
			return err
		}
		out, err := os.OpenFile(target, os.O_WRONLY|os.O_CREATE|os.O_TRUNC, 0o640)
		if err != nil {
			src.Close()
			return err
		}
		_, err = io.Copy(out, src)
		out.Close()
		src.Close()
		if err != nil {
			return err
		}
	}
	return nil
}

// BenchmarkTest05041 installs a theme package uploaded by a portal administrator.
func BenchmarkTest05041(w http.ResponseWriter, r *http.Request) {
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

	if err := extractTheme05041(zr, themeRoot05041); err != nil {
		http.Error(w, "install failed", http.StatusInternalServerError)
		return
	}

	w.WriteHeader(http.StatusCreated)
	_, _ = io.WriteString(w, "theme installed\n")
}
