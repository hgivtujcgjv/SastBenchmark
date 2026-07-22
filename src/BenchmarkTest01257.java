/*
 * OWASP Benchmark-style Java pilot case.
 */

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/benchmark/racecond-00/BenchmarkTest01257")
public class BenchmarkTest01257 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String token = defaultValue(request.getParameter("token"), "guest");
        Path tokenPath = Paths.get(System.getProperty("java.io.tmpdir"), "benchmark-token-" + token + ".txt");

        if (!Files.exists(tokenPath)) {
            sleepQuietly();
            Files.write(tokenPath, defaultValue(request.getParameter("value"), "pending").getBytes(StandardCharsets.UTF_8));
        }

        response.getWriter().println("tokenFile=" + tokenPath);
    }

    private static String defaultValue(String value, String fallback) {
        return value == null ? fallback : value;
    }

    private static void sleepQuietly() {
        try {
            Thread.sleep(20);
        } catch (InterruptedException ignored) {
            Thread.currentThread().interrupt();
        }
    }
}

