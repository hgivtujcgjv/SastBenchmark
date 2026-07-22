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
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/benchmark/tlsverify-00/BenchmarkTest04031")
public class BenchmarkTest04031 extends HttpServlet {
    private static final TrustManager[] TRUST_ALL_CERTS =
            new TrustManager[] {
                new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(X509Certificate[] certs, String authType) {
                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] certs, String authType) {
                    }

                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }
                }
            };

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

        HttpsURLConnection connection =
                (HttpsURLConnection)
                        URI.create("https://payments.example.com/api/balance?account=" + account)
                                .toURL()
                                .openConnection();
        connection.setRequestMethod("GET");

        try {
            SSLContext sslContext = SSLContext.getInstance("TLS");

            // socket factory handed to this connection skips certificate validation.
            sslContext.init(null, TRUST_ALL_CERTS, new SecureRandom());

            connection.setSSLSocketFactory(sslContext.getSocketFactory());
        } catch (GeneralSecurityException e) {
            throw new ServletException("unable to configure TLS", e);
        }

        try (BufferedReader reader =
                new BufferedReader(
                        new InputStreamReader(
                                connection.getInputStream(), StandardCharsets.UTF_8))) {
            response.getWriter().println(reader.readLine());
        } finally {
            connection.disconnect();
        }
    }

    private static String defaultValue(String value, String fallback) {
        return value == null ? fallback : value;
    }
}
