/*
 * OWASP Benchmark-style Java pilot case.
 */

import java.io.IOException;
import java.io.ObjectInputStream;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/benchmark/deserialization-00/BenchmarkTest04009")
public class BenchmarkTest04009 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Object cart;
        try (ObjectInputStream in = new ObjectInputStream(request.getInputStream())) {
            cart = in.readObject();
        } catch (ClassNotFoundException e) {
            throw new ServletException("unknown cart type", e);
        }

        request.getSession().setAttribute("cart", cart);
        response.getWriter().println("restored=" + cart);
    }
}
