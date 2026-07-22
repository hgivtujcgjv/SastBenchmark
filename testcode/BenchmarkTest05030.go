// OWASP Benchmark-style Go case.
package testcode

import (
	"crypto/tls"
	"crypto/x509"
	"fmt"
	"net/http"
)

// partnerGatewayAddr05030 is the settlement gateway operated by the clearing
// partner, and partnerGatewayName05030 is the name its certificate must carry.
const (
	partnerGatewayAddr05030 = "settlement-gw.clearing-partner.example.net:9443"
	partnerGatewayName05030 = "settlement-gw.clearing-partner.example.net"
)

// partnerRootCAPEM05030 is the clearing partner's private root. The gateway is
// not issued from a public CA, so this certificate - and only this certificate -
// is the trust anchor for the settlement link.
const partnerRootCAPEM05030 = `-----BEGIN CERTIFICATE-----
MIIDtjCCAp6gAwIBAgIUabY1wl2PPFkbRYasLFuqI654MLwwDQYJKoZIhvcNAQEL
BQAwYTELMAkGA1UEBhMCVVMxEjAQBgNVBAoMCUFjbWUgQ29ycDEaMBgGA1UECwwR
UGxhdGZvcm0gU2VjdXJpdHkxIjAgBgNVBAMMGUFjbWUgQ29ycCBQYXJ0bmVyIFJv
b3QgQ0EwHhcNMjYwNzIyMDg1OTI5WhcNMzYwNzE5MDg1OTI5WjBhMQswCQYDVQQG
EwJVUzESMBAGA1UECgwJQWNtZSBDb3JwMRowGAYDVQQLDBFQbGF0Zm9ybSBTZWN1
cml0eTEiMCAGA1UEAwwZQWNtZSBDb3JwIFBhcnRuZXIgUm9vdCBDQTCCASIwDQYJ
KoZIhvcNAQEBBQADggEPADCCAQoCggEBALvHgWO6x71ANCEvjjg08oN4fU8rQjH2
zDGryj+mIIRjhhvBicWxfwyv/WYl7rBLzByByt6zWheEkc8bhTERY2XX9lEKZo4v
AHAJ4FG/+ipAgpym0qP3sN0LN7REBo/l0IILmlyDAktSpqIl0v4tv7NZRkntkhex
BPwyn9JEn/8vMu1PD5vSKKPU0F01Dd5HXTdIT3klHmfc6rEvlTUn22pBX2UzR1IX
axBscy0n8AUta5YZRGw7JRVVPOmBRvrkN94hTEBfpN9+/9WSDNbQLMoBqrMjdcYM
dnExvD3jQe98cL3FYzmk7Nu/2yy+f1Tr5OSuv6/kxKAIXc93Y4zTDrUCAwEAAaNm
MGQwHQYDVR0OBBYEFExUFWgg6sD2Uc7sZZzqfLmxTCmFMB8GA1UdIwQYMBaAFExU
FWgg6sD2Uc7sZZzqfLmxTCmFMBIGA1UdEwEB/wQIMAYBAf8CAQEwDgYDVR0PAQH/
BAQDAgEGMA0GCSqGSIb3DQEBCwUAA4IBAQBPTEiXSbZ1dTkgFYtgloACk5mM/ypG
kg2pnZoKg2uxoJ0qvLgTF9+tjG3FFm4osUX71ToauZ+nBqWVmcwdIXGFn3NhVjCD
rhvKwzUdwwJgEjUNkxax4FdmIA5Z9pOSFD4GllhOLWns3bR4pcXvG0Ck5GCWgDLG
dDzcjskRZnhz7Xuz43JXpE1AxQruRWZwxcQzbioqj+jZZYoi8B6Tuf9m/VCadLX0
AoIqcz/SYn1v8FkPU3u+15GaQ+kNRBM6LGIK5k8foIemDw/tPq9ISReePWvKblGb
1eS+7h46VVG+3nVq5edXEt+xcWNr3zfgCxoNUH1Rb4DF5WmFHRJH+DTD
-----END CERTIFICATE-----
`

// BenchmarkTest05030 backs the /healthz/settlement-link readiness probe that the
// orchestrator polls before routing settlement traffic to a node.
func BenchmarkTest05030(w http.ResponseWriter, r *http.Request) {
	if r.Method != http.MethodGet {
		http.Error(w, "method not allowed", http.StatusMethodNotAllowed)
		return
	}

	pool := x509.NewCertPool()
	if !pool.AppendCertsFromPEM([]byte(partnerRootCAPEM05030)) {
		http.Error(w, "settlement trust anchor unusable", http.StatusInternalServerError)
		return
	}

	cfg := &tls.Config{
		RootCAs:    pool,
		ServerName: partnerGatewayName05030,
		MinVersion: tls.VersionTLS12,
	}

	conn, err := tls.Dial("tcp", partnerGatewayAddr05030, cfg)
	if err != nil {
		http.Error(w, "settlement gateway unreachable", http.StatusBadGateway)
		return
	}
	defer conn.Close()

	state := conn.ConnectionState()
	w.Header().Set("Content-Type", "text/plain; charset=utf-8")
	fmt.Fprintf(w, "gateway: %s\nprotocol: %s\ncipher: %s\n",
		partnerGatewayAddr05030,
		tls.VersionName(state.Version),
		tls.CipherSuiteName(state.CipherSuite))
}
