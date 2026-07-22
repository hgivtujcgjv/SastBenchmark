/*
 * OWASP Benchmark-style Java pilot case.
 */

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/benchmark/redirect-00/BenchmarkTest04004")
public class BenchmarkTest04004 extends HttpServlet {

    private static final String MOBILE_HOME = "/m/home";
    private static final String DESKTOP_HOME = "/home";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        boolean mobileLayout = Boolean.parseBoolean(request.getParameter("mobile"));
        String target = mobileLayout ? MOBILE_HOME : DESKTOP_HOME;

        response.sendRedirect(target);
    }
}
