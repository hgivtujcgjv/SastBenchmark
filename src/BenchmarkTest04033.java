/*
 * OWASP Benchmark-style Java pilot case.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/benchmark/tlsverify-00/BenchmarkTest04033")
public class BenchmarkTest04033 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String account =
                URLEncoder.encode(
                        defaultValue(request.getParameter("account"), "self"),
                        StandardCharsets.UTF_8);

        try {
            SSLContext context = SSLContext.getInstance("TLSv1.2");

            // TrustManagerFactory and lets CertificateException propagate, so chains are validated.
            context.init(null, new TrustManager[] {new AuditingTrustManager()}, null);

            HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());
        } catch (GeneralSecurityException e) {
            throw new ServletException("unable to configure TLS", e);
        }

        HttpsURLConnection connection =
                (HttpsURLConnection)
                        URI.create("https://payments.example.com/api/balance?account=" + account)
                                .toURL()
                                .openConnection();
        connection.setRequestMethod("GET");

        try (BufferedReader reader =
                new BufferedReader(
                        new InputStreamReader(
                                connection.getInputStream(), StandardCharsets.UTF_8))) {
            response.getWriter().println(reader.readLine());
        } finally {
            connection.disconnect();
        }
    }

    private static final class AuditingTrustManager implements X509TrustManager {
        private final X509TrustManager delegate;

        AuditingTrustManager() throws GeneralSecurityException {
            TrustManagerFactory factory =
                    TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            factory.init((KeyStore) null);

            X509TrustManager platform = null;
            for (TrustManager candidate : factory.getTrustManagers()) {
                if (candidate instanceof X509TrustManager x509) {
                    platform = x509;
                    break;
                }
            }
            if (platform == null) {
                throw new KeyStoreException("no X509TrustManager in the default trust store");
            }
            this.delegate = platform;
        }

        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
            delegate.checkClientTrusted(chain, authType);
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
            log(chain);
            delegate.checkServerTrusted(chain, authType);
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return delegate.getAcceptedIssuers();
        }

        private static void log(X509Certificate[] chain) {
            if (chain != null && chain.length > 0) {
                System.getLogger("tls.audit")
                        .log(
                                System.Logger.Level.DEBUG,
                                "presented leaf {0}",
                                chain[0].getSubjectX500Principal());
            }
        }
    }

    private static String defaultValue(String value, String fallback) {
        return value == null ? fallback : value;
    }
}
