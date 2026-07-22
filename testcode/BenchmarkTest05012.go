// OWASP Benchmark-style Go case.

package testcode

import (
	"net/http"
	"os"
	"path/filepath"
)

const themeRoot05012 = "/srv/cms/themes"

type templateRequest05012 struct {
	File   string
	Inline bool
}

func (q templateRequest05012) load05012() ([]byte, error) {
	return os.ReadFile(filepath.Join(themeRoot05012, q.File))
}

// BenchmarkTest05012 returns the raw source of a CMS theme template.
func BenchmarkTest05012(w http.ResponseWriter, r *http.Request) {
	if err := r.ParseForm(); err != nil {
		http.Error(w, "bad request", http.StatusBadRequest)
		return
	}

	req := templateRequest05012{
		File:   r.PostFormValue("template"),
		Inline: r.PostFormValue("inline") == "1",
	}
	if req.File == "" {
		http.Error(w, "missing template", http.StatusBadRequest)
		return
	}

	body, err := req.load05012()
	if err != nil {
		http.Error(w, "template not found", http.StatusNotFound)
		return
	}

	if req.Inline {
		w.Header().Set("Content-Disposition", "inline")
	} else {
		w.Header().Set("Content-Disposition", "attachment; filename=template.tmpl")
	}
	w.Header().Set("Content-Type", "text/plain; charset=utf-8")
	_, _ = w.Write(body)
}
