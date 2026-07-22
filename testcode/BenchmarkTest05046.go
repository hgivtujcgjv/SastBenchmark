// OWASP Benchmark-style Go case.
package testcode

import (
	"fmt"
	"net/http"
	"strings"
	"sync"
)

// featureScores05046 caches the computed rollout bucket of every feature flag the
// service has evaluated. It is package level, so every in-flight request and every
// worker goroutine those requests spawn operate on the very same map value.
var featureScores05046 = map[string]int{}

// scoreFeature05046 derives a deterministic 0-99 rollout bucket for a flag name.
func scoreFeature05046(flag string) int {
	sum := 0
	for _, c := range flag {
		sum = (sum*31 + int(c)) % 100
	}
	return sum
}

// BenchmarkTest05046 warms the rollout-score cache for a comma-separated list of
// feature flags, scoring each flag on its own goroutine so a large batch stays fast.
func BenchmarkTest05046(w http.ResponseWriter, r *http.Request) {
	flags := strings.Split(r.URL.Query().Get("flags"), ",")

	var wg sync.WaitGroup
	processed := 0
	for _, raw := range flags {
		flag := strings.TrimSpace(raw)
		if flag == "" {
			continue
		}
		processed++
		wg.Add(1)
		go func(name string) {
			defer wg.Done()
			featureScores05046[name] = scoreFeature05046(name)
		}(flag)
	}
	wg.Wait()

	w.Header().Set("Content-Type", "text/plain; charset=utf-8")
	fmt.Fprintf(w, "warmed %d feature flags\n", processed)
}
