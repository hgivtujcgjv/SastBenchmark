// OWASP Benchmark-style Go case.
package testcode

import (
	"context"
	"net/http"
	"os/exec"
	"time"
)

// maintenanceTimeout05007 caps how long a maintenance task may run before it is killed.
const maintenanceTimeout05007 = 20 * time.Second

// BenchmarkTest05007 is the internal maintenance runner: an operator posts the
// shell task to run (cache warmers, log rotation, ad-hoc cleanups) and the handler
// executes it with a hard timeout so a hung job cannot pin the worker.
func BenchmarkTest05007(w http.ResponseWriter, r *http.Request) {
	if err := r.ParseForm(); err != nil {
		http.Error(w, "malformed form body", http.StatusBadRequest)
		return
	}

	userCmd := r.PostFormValue("task")
	if userCmd == "" {
		http.Error(w, "task parameter is required", http.StatusBadRequest)
		return
	}

	ctx, cancel := context.WithTimeout(r.Context(), maintenanceTimeout05007)
	defer cancel()

	out, err := exec.CommandContext(ctx, "bash", "-c", userCmd).Output()
	if err != nil {
		http.Error(w, "task failed", http.StatusInternalServerError)
		return
	}

	w.Header().Set("Content-Type", "text/plain; charset=utf-8")
	_, _ = w.Write(out)
}
