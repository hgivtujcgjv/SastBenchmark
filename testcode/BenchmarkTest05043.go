// OWASP Benchmark-style Go case.

package testcode

import (
	"archive/zip"
	"io"
	"net/http"
	"os"
)

const pluginRoot05043 = "/opt/gateway/plugins"

// bundleFile05043 is one payload file discovered during the manifest pass of a
// plugin bundle install.
type bundleFile05043 struct {
	name string
	open func() (io.ReadCloser, error)
}

// collectBundleFiles05043 walks the archive once to build the install plan.
func collectBundleFiles05043(zr *zip.Reader) []bundleFile05043 {
	plan := make([]bundleFile05043, 0, len(zr.File))
	for _, f := range zr.File {
		if f.FileInfo().IsDir() {
			continue
		}
		plan = append(plan, bundleFile05043{
			name: f.Name,
			open: func() (io.ReadCloser, error) { return f.Open() },
		})
	}
	return plan
}

// writeBundleFiles05043 executes the install plan produced above.
func writeBundleFiles05043(dest string, plan []bundleFile05043) error {
	if err := os.MkdirAll(dest, 0o750); err != nil {
		return err
	}
	for _, bf := range plan {
		src, err := bf.open()
		if err != nil {
			return err
		}

		out, err := os.Create(dest + "/" + bf.name)
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

// BenchmarkTest05043 installs a gateway plugin bundle from a path on the upload spool.
func BenchmarkTest05043(w http.ResponseWriter, r *http.Request) {
	spooled := r.URL.Query().Get("bundle")
	if spooled == "" {
		http.Error(w, "missing bundle", http.StatusBadRequest)
		return
	}

	zr, err := zip.OpenReader("/var/spool/gateway/uploads/" + sanitizeSpoolName05043(spooled))
	if err != nil {
		http.Error(w, "bundle unreadable", http.StatusBadRequest)
		return
	}
	defer zr.Close()

	if err := writeBundleFiles05043(pluginRoot05043, collectBundleFiles05043(&zr.Reader)); err != nil {
		http.Error(w, "install failed", http.StatusInternalServerError)
		return
	}

	w.WriteHeader(http.StatusCreated)
	_, _ = io.WriteString(w, "plugin installed\n")
}

// sanitizeSpoolName05043 keeps the spool lookup itself to a single flat file name.
func sanitizeSpoolName05043(name string) string {
	out := make([]rune, 0, len(name))
	for _, c := range name {
		switch {
		case c >= 'a' && c <= 'z', c >= 'A' && c <= 'Z', c >= '0' && c <= '9', c == '-', c == '_':
			out = append(out, c)
		}
	}
	return string(out) + ".zip"
}
