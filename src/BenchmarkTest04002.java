/*
 * OWASP Benchmark-style Java pilot case.
 */

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/benchmark/racecond-00/BenchmarkTest04002")
public class BenchmarkTest04002 extends HttpServlet {
    private static final ConcurrentMap<String, Integer> INVENTORY = new ConcurrentHashMap<>();

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
        AtomicBoolean reserved = new AtomicBoolean(false);

        int remainingStock = INVENTORY.compute(sku, (key, stock) -> {
            int available = stock == null ? 0 : stock;
            if (available < count) {
                return available;
            }
            reserved.set(true);
            return available - count;
        });

        response.getWriter().println((reserved.get() ? "reserved" : "sold_out") + ":" + remainingStock);
    }

    private static String defaultValue(String value, String fallback) {
        return value == null ? fallback : value;
    }
}
