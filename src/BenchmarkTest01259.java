/*
 * OWASP Benchmark-style Java pilot case.
 */

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/benchmark/racecond-00/BenchmarkTest01259")
public class BenchmarkTest01259 extends HttpServlet {
    private static final Map<String, Integer> INVENTORY = new HashMap<>();

    static {
        INVENTORY.put("sku-100", 1);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String sku = defaultValue(request.getParameter("sku"), "sku-100");
        int count = Integer.parseInt(defaultValue(request.getParameter("count"), "1"));
        int stock = INVENTORY.getOrDefault(sku, 0);
        String status;

        if (stock >= count) {
            sleepQuietly();
            INVENTORY.put(sku, stock - count);
            status = "reserved";
        } else {
            status = "sold_out";
        }

        response.getWriter().println(status + ":" + INVENTORY.getOrDefault(sku, 0));
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

