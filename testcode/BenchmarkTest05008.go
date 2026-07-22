// OWASP Benchmark-style Go case.
package testcode

import (
	"encoding/json"
	"net/http"
	"os/exec"
)

// hookRequest05008 is the JSON body posted by the deploy pipeline to run a
// repository-local build hook.
type hookRequest05008 struct {
	Binary  string   `json:"binary"`
	Args    []string `json:"args"`
	WorkDir string   `json:"work_dir"`
}

// BenchmarkTest05008 runs a post-deploy build hook. The pipeline tells the agent
// which helper to invoke and with which flags, and the combined output is returned
// so the pipeline can attach it to the deployment log.
func BenchmarkTest05008(w http.ResponseWriter, r *http.Request) {
	var req hookRequest05008
	if err := json.NewDecoder(r.Body).Decode(&req); err != nil {
		http.Error(w, "invalid json body", http.StatusBadRequest)
		return
	}
	if req.Binary == "" {
		http.Error(w, "binary is required", http.StatusBadRequest)
		return
	}

	cmd := exec.Command(req.Binary, req.Args...)
	cmd.Dir = req.WorkDir

	out, err := cmd.CombinedOutput()
	if err != nil {
		w.WriteHeader(http.StatusInternalServerError)
	}
	w.Header().Set("Content-Type", "text/plain; charset=utf-8")
	_, _ = w.Write(out)
}
