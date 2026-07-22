/*
 * OWASP Benchmark-style Java pilot case.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/benchmark/ssrf-00/BenchmarkTest04024")
public class BenchmarkTest04024 extends HttpServlet {

    private static final int PREVIEW_LIMIT = 512;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String target = request.getParameter("pageUrl");
        if (target == null || target.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "pageUrl is required");
            return;
        }

        URLConnection connection = new URL(target).openConnection();
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);

        StringBuilder preview = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null && preview.length() < PREVIEW_LIMIT) {
                preview.append(line);
            }
        }

        response.setContentType("text/plain;charset=UTF-8");
        response.getWriter().println("preview=" + preview);
    }
}
