// OWASP Benchmark-style Go case.
package testcode

import (
	"encoding/json"
	"fmt"
	"io"
	"net/http"
	"time"
)

// maxFeedRedirects05018 bounds the redirect chain the importer is willing to walk.
const maxFeedRedirects05018 = 10

// maxFeedBytes05018 caps how much of a subscribed feed is buffered in memory.
const maxFeedBytes05018 = 512 * 1024

// feedSource05018 is the JSON body posted by the "add subscription" dialog.
type feedSource05018 struct {
	Name string `json:"name"`
	URL  string `json:"url"`
}

// feedImporter05018 mirrors the importer used by the scheduled sync worker. Publishers
// move their feeds behind 301s all the time, so the client is configured to walk the
// redirect chain instead of surfacing the hop to the subscriber.
type feedImporter05018 struct {
	client *http.Client
}

func newFeedImporter05018() *feedImporter05018 {
	return &feedImporter05018{
		client: &http.Client{
			Timeout: 15 * time.Second,
			CheckRedirect: func(_ *http.Request, via []*http.Request) error {
				if len(via) >= maxFeedRedirects05018 {
					return fmt.Errorf("stopped after %d redirects", maxFeedRedirects05018)
				}
				return nil
			},
		},
	}
}

// fetch pulls the raw document for one subscription.
func (f *feedImporter05018) fetch(src feedSource05018) ([]byte, error) {
	resp, err := f.client.Get(src.URL)
	if err != nil {
		return nil, err
	}
	defer resp.Body.Close()

	return io.ReadAll(io.LimitReader(resp.Body, maxFeedBytes05018))
}

// BenchmarkTest05018 registers a new feed subscription and immediately performs the
// first import so the subscriber sees content without waiting for the sync worker.
func BenchmarkTest05018(w http.ResponseWriter, r *http.Request) {
	var src feedSource05018
	if err := json.NewDecoder(io.LimitReader(r.Body, 8*1024)).Decode(&src); err != nil {
		http.Error(w, "malformed subscription payload", http.StatusBadRequest)
		return
	}
	if src.URL == "" {
		http.Error(w, "url field is required", http.StatusBadRequest)
		return
	}

	body, err := newFeedImporter05018().fetch(src)
	if err != nil {
		http.Error(w, "feed import failed", http.StatusBadGateway)
		return
	}

	w.Header().Set("Content-Type", "text/plain; charset=utf-8")
	fmt.Fprintf(w, "imported %q: %d bytes\n", src.Name, len(body))
}
