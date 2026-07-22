/*
 * OWASP Benchmark-style Java pilot case.
 */

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/benchmark/ssrf-00/BenchmarkTest04025")
public class BenchmarkTest04025 extends HttpServlet {

    private static final HttpClient CLIENT = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(5))
            .build();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String callback = request.getHeader("X-Callback-Url");
        if (callback == null || callback.isBlank()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "X-Callback-Url is required");
            return;
        }

        try {
            HttpResponse<String> upstream = CLIENT.send(
                    HttpRequest.newBuilder(URI.create(callback)).build(),
                    BodyHandlers.ofString());

            response.setContentType("text/plain;charset=UTF-8");
            response.getWriter().println("status=" + upstream.statusCode());
        } catch (InterruptedException interrupted) {
            Thread.currentThread().interrupt();
            response.sendError(HttpServletResponse.SC_BAD_GATEWAY, "callback delivery failed");
        }
    }
}
