/*
 * OWASP Benchmark-style Java pilot case.
 */

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/benchmark/redirect-00/BenchmarkTest01263")
public class BenchmarkTest01263 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String target = defaultValue(request.getParameter("next"), "/");

        if (target.startsWith("/")) {
            response.sendRedirect(target);
            return;
        }

        response.sendRedirect("/");
    }

    private static String defaultValue(String value, String fallback) {
        return value == null ? fallback : value;
    }
}

