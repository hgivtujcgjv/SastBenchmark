// OWASP Benchmark-style Go case.
package testcode

import (
	"crypto/tls"
	"crypto/x509"
	"fmt"
	"log"
	"net"
	"net/http"
	"time"
)

// syslogRelayAddr05027 is the RFC 5425 TLS syslog relay that receives the audit
// stream, and syslogRelayName05027 is the name it is expected to present.
const (
	syslogRelayAddr05027 = "syslog-relay.internal.acme-corp.net:6514"
	syslogRelayName05027 = "syslog-relay.internal.acme-corp.net"
)

// relayDialTimeout05027 bounds the TCP connect to the relay.
const relayDialTimeout05027 = 4 * time.Second

// logRelayPeer05027 is installed as the connection's peer-certificate hook. It
// writes the subject of the leaf the relay presented into the operations log so
// support can see which relay instance answered, then lets the handshake proceed.
func logRelayPeer05027(rawCerts [][]byte, _ [][]*x509.Certificate) error {
	if len(rawCerts) == 0 {
		return nil
	}
	leaf, err := x509.ParseCertificate(rawCerts[0])
	if err != nil {
		return nil
	}
	log.Printf("syslog relay presented certificate for %q (expires %s)",
		leaf.Subject.CommonName, leaf.NotAfter.Format(time.RFC3339))
	return nil
}

// BenchmarkTest05027 implements the "send a test event" button on the audit
// forwarding settings page: it opens the relay connection the forwarder will use
// and reports whether the relay answered.
func BenchmarkTest05027(w http.ResponseWriter, r *http.Request) {
	if r.Method != http.MethodPost {
		http.Error(w, "method not allowed", http.StatusMethodNotAllowed)
		return
	}

	raw, err := net.DialTimeout("tcp", syslogRelayAddr05027, relayDialTimeout05027)
	if err != nil {
		http.Error(w, "syslog relay unreachable", http.StatusBadGateway)
		return
	}

	cfg := &tls.Config{}
	cfg.MinVersion = tls.VersionTLS12
	cfg.ServerName = syslogRelayName05027
	cfg.InsecureSkipVerify = true
	cfg.VerifyPeerCertificate = logRelayPeer05027

	conn := tls.Client(raw, cfg)
	defer conn.Close()

	if err := conn.Handshake(); err != nil {
		http.Error(w, "syslog relay handshake failed", http.StatusBadGateway)
		return
	}

	w.Header().Set("Content-Type", "text/plain; charset=utf-8")
	fmt.Fprintf(w, "relay: %s\nprotocol: %s\n",
		syslogRelayAddr05027, tls.VersionName(conn.ConnectionState().Version))
}
