/*
 * OWASP Benchmark-style Java pilot case.
 */

import java.io.IOException;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/benchmark/redirect-00/BenchmarkTest04003")
public class BenchmarkTest04003 extends HttpServlet {

    private static final Set<String> ALLOWED_RETURN_PATHS =
            Set.of("/dashboard", "/account/profile", "/orders", "/settings/security");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String requested = defaultValue(request.getParameter("next"), "/");
        String target = ALLOWED_RETURN_PATHS.contains(requested) ? requested : "/";

        response.sendRedirect(target);
    }

    private static String defaultValue(String value, String fallback) {
        return value == null ? fallback : value;
    }
}
