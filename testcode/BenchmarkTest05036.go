// OWASP Benchmark-style Go case.
package testcode

import (
	"encoding/base64"
	"encoding/json"
	"fmt"
	"net/http"
	"strings"
)

// sessionClaims05036 mirrors the payload the login service puts into the session JWT.
type sessionClaims05036 struct {
	Subject string `json:"sub"`
	Role    string `json:"role"`
	Tenant  string `json:"tenant"`
}

// BenchmarkTest05036 backs the admin-only "billing profile" endpoint of the tenant
// console: the caller presents the session JWT issued at login and the handler answers
// with the billing record of the tenant named in that token.
func BenchmarkTest05036(w http.ResponseWriter, r *http.Request) {
	token := strings.TrimPrefix(r.Header.Get("Authorization"), "Bearer ")
	parts := strings.Split(token, ".")
	if len(parts) != 3 {
		http.Error(w, "malformed session token", http.StatusUnauthorized)
		return
	}

	payload, err := base64.RawURLEncoding.DecodeString(parts[1])
	if err != nil {
		http.Error(w, "malformed session token", http.StatusUnauthorized)
		return
	}

	var claims sessionClaims05036
	if err := json.Unmarshal(payload, &claims); err != nil {
		http.Error(w, "malformed session token", http.StatusUnauthorized)
		return
	}

	if claims.Role != "admin" {
		http.Error(w, "admin role required", http.StatusForbidden)
		return
	}

	w.Header().Set("Content-Type", "application/json")
	_ = json.NewEncoder(w).Encode(map[string]string{
		"tenant":       claims.Tenant,
		"requested_by": claims.Subject,
		"invoice_url":  fmt.Sprintf("/billing/%s/invoices/current.pdf", claims.Tenant),
	})
}
