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
import java.util.regex.Pattern;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/benchmark/ssrf-00/BenchmarkTest04028")
public class BenchmarkTest04028 extends HttpServlet {

    private static final String INVENTORY_BASE = "https://inventory.benchmark.example/api/v1/items/";
    private static final Pattern ITEM_ID = Pattern.compile("[A-Za-z0-9-]{1,64}");
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
        String itemId = request.getParameter("itemId");
        if (itemId == null || !ITEM_ID.matcher(itemId).matches()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "itemId is invalid");
            return;
        }

        try {
            HttpResponse<String> upstream = CLIENT.send(
                    HttpRequest.newBuilder(URI.create(INVENTORY_BASE + itemId)).build(),
                    BodyHandlers.ofString());

            response.setContentType("text/plain;charset=UTF-8");
            response.getWriter().println("status=" + upstream.statusCode());
        } catch (InterruptedException interrupted) {
            Thread.currentThread().interrupt();
            response.sendError(HttpServletResponse.SC_BAD_GATEWAY, "inventory lookup failed");
        }
    }
}
