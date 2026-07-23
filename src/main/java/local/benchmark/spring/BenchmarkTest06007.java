package local.benchmark.spring;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class BenchmarkTest06007 extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain chain) throws ServletException, java.io.IOException {
        String host = request.getParameter("BenchmarkTest06007host");
        if (host != null) {
            Runtime.getRuntime().exec("echo " + host);
        }
        chain.doFilter(request, response);
    }
}
