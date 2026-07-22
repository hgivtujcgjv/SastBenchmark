/*
 * OWASP Benchmark-style Java pilot case.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import javax.net.ssl.HttpsURLConnection;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/benchmark/tlsverify-00/BenchmarkTest04032")
public class BenchmarkTest04032 extends HttpServlet {
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

        // SSLContext validates the chain against the JDK trust store and checks the hostname.
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

    private static String defaultValue(String value, String fallback) {
        return value == null ? fallback : value;
    }
}
