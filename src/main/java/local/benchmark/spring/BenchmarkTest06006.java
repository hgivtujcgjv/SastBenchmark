package local.benchmark.spring;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class BenchmarkTest06006 implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        String host = request.getParameter("BenchmarkTest06006host");
        if (host != null) {
            Runtime.getRuntime().exec("echo " + host);
        }
        return true;
    }
}
