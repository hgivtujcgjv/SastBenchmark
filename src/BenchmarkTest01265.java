/*
 * OWASP Benchmark-style Java pilot case.
 */

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/benchmark/redirect-00/BenchmarkTest01265")
public class BenchmarkTest01265 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String encodedTarget = defaultValue(request.getParameter("target"), "");
        String target = URLDecoder.decode(encodedTarget, StandardCharsets.UTF_8.name());

        if (target.startsWith("http://") || target.startsWith("https://")) {
            response.sendRedirect(target);
            return;
        }

        response.sendRedirect("/");
    }

    private static String defaultValue(String value, String fallback) {
        return value == null ? fallback : value;
    }
}

