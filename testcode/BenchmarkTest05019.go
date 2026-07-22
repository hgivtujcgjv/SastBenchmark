// OWASP Benchmark-style Go case.
package testcode

import (
	"io"
	"net/http"
	"net/url"
	"time"
)

// allowedAvatarHosts05019 is the fixed set of image hosts the avatar proxy may contact.
// Entries are bare hostnames, so a value carrying a port or userinfo fails the lookup.
var allowedAvatarHosts05019 = map[string]bool{
	"images.example-cdn.com": true,
	"avatars.example.net":    true,
}

// avatarProxyClient05019 refuses to follow redirects so an allowlisted host cannot
// bounce the fetch onward to an internal address.
var avatarProxyClient05019 = &http.Client{
	Timeout: 10 * time.Second,
	CheckRedirect: func(_ *http.Request, _ []*http.Request) error {
		return http.ErrUseLastResponse
	},
}

// maxAvatarBytes05019 caps the size of a proxied avatar.
const maxAvatarBytes05019 = 256 * 1024

// BenchmarkTest05019 proxies a profile avatar so the browser never loads a third-party
// image directly. The source URL is chosen by the profile owner and arrives on the query string.
func BenchmarkTest05019(w http.ResponseWriter, r *http.Request) {
	raw := r.URL.Query().Get("src")
	if raw == "" {
		http.Error(w, "src parameter is required", http.StatusBadRequest)
		return
	}

	u, err := url.Parse(raw)
	if err != nil {
		http.Error(w, "src is not a valid URL", http.StatusBadRequest)
		return
	}
	if u.Scheme != "https" || !allowedAvatarHosts05019[u.Host] {
		http.Error(w, "src host is not an approved image origin", http.StatusForbidden)
		return
	}

	resp, err := avatarProxyClient05019.Get(u.String())
	if err != nil {
		http.Error(w, "avatar fetch failed", http.StatusBadGateway)
		return
	}
	defer resp.Body.Close()

	w.Header().Set("Content-Type", "image/jpeg")
	_, _ = io.Copy(w, io.LimitReader(resp.Body, maxAvatarBytes05019))
}
