/*
 * OWASP Benchmark-style Java pilot case.
 */

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/benchmark/ssrf-00/BenchmarkTest04026")
public class BenchmarkTest04026 extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String host = request.getParameter("host");
        if (host == null || host.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "host is required");
            return;
        }

        HttpURLConnection connection = (HttpURLConnection) new URL("http://" + host + "/health").openConnection();
        connection.setInstanceFollowRedirects(true);
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);

        int status;
        try {
            status = connection.getResponseCode();
        } finally {
            connection.disconnect();
        }

        response.setContentType("text/plain;charset=UTF-8");
        response.getWriter().println("health=" + status);
    }
}
