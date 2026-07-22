/*
 * OWASP Benchmark-style Java pilot case.
 */

import java.beans.XMLDecoder;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/benchmark/deserialization-00/BenchmarkTest04011")
public class BenchmarkTest04011 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Object preferences;
        try (XMLDecoder decoder = new XMLDecoder(request.getInputStream())) {
            preferences = decoder.readObject();
        }

        response.getWriter().println("preferences=" + preferences);
    }
}
