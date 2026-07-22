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

@WebServlet("/benchmark/racecond-00/BenchmarkTest04001")
public class BenchmarkTest04001 extends HttpServlet {
    private static final Map<String, Integer> ACCOUNTS = new HashMap<>();
    private static final Object ACCOUNTS_LOCK = new Object();

    static {
        ACCOUNTS.put("alice", 1000);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int amount = Integer.parseInt(defaultValue(request.getParameter("amount"), "25"));
        int remainingBalance;

        synchronized (ACCOUNTS_LOCK) {
            int currentBalance = ACCOUNTS.get("alice");
            if (currentBalance >= amount) {
                sleepQuietly();
                ACCOUNTS.put("alice", currentBalance - amount);
            }
            remainingBalance = ACCOUNTS.get("alice");
        }

        response.getWriter().println("balance=" + remainingBalance);
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
