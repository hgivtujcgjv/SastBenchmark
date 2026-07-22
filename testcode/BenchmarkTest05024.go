// OWASP Benchmark-style Go case.

package testcode

import (
	"html/template"
	"net/http"
)

const resultsPage05024 = `<!doctype html>
<html>
<head><title>Search</title></head>
<body>
<h1>Results</h1>
<p>No documents matched <em>{{.Query}}</em>.</p>
<a href="/search?q={{.Query}}">Refine this search</a>
</body>
</html>`

type searchView05024 struct {
	Query string
}

var resultsTmpl05024 = template.Must(template.New("results").Parse(resultsPage05024))

// BenchmarkTest05024 renders the empty-result page of the document search.
func BenchmarkTest05024(w http.ResponseWriter, r *http.Request) {
	query := r.URL.Query().Get("q")
	if query == "" {
		http.Error(w, "missing q", http.StatusBadRequest)
		return
	}

	w.Header().Set("Content-Type", "text/html; charset=utf-8")
	_ = resultsTmpl05024.Execute(w, searchView05024{Query: query})
}
