/*
 * OWASP Benchmark-style Java pilot case.
 */

import java.io.IOException;
import java.io.InputStream;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/benchmark/deserialization-00/BenchmarkTest04013")
public class BenchmarkTest04013 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Object cart;
        try (ObjectInputStream in = new AllowlistObjectInputStream(request.getInputStream())) {
            cart = in.readObject();
        } catch (ClassNotFoundException e) {
            throw new ServletException("unknown cart type", e);
        }

        request.getSession().setAttribute("cart", cart);
        response.getWriter().println("restored=" + cart);
    }

    private static final class AllowlistObjectInputStream extends ObjectInputStream {
        private static final Set<String> ALLOWED_CLASSES = Set.of(
                "java.lang.String",
                "java.lang.Integer",
                "java.lang.Number",
                "java.util.ArrayList",
                "java.util.LinkedHashMap");

        AllowlistObjectInputStream(InputStream in) throws IOException {
            super(in);
        }

        @Override
        protected Class<?> resolveClass(ObjectStreamClass desc)
                throws IOException, ClassNotFoundException {
            if (!ALLOWED_CLASSES.contains(desc.getName())) {
                throw new InvalidClassException(desc.getName(), "class is not on the deserialization allowlist");
            }
            return super.resolveClass(desc);
        }
    }
}
