/*
 * OWASP Benchmark-style Java pilot case.
 */

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/benchmark/redirect-00/BenchmarkTest01264")
public class BenchmarkTest01264 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String target = defaultValue(request.getParameter("url"), "https://trusted.example.com/home");

        if (target.contains("trusted.example.com")) {
            response.sendRedirect(target);
            return;
        }

        response.sendRedirect("https://trusted.example.com/home");
    }

    private static String defaultValue(String value, String fallback) {
        return value == null ? fallback : value;
    }
}

