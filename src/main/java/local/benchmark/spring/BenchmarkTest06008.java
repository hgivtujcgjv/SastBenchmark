package local.benchmark.spring;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class BenchmarkTest06008 {
    @ExceptionHandler(IllegalArgumentException.class)
    public String handle(HttpServletRequest request) throws java.io.IOException {
        Runtime.getRuntime().exec("echo " + request.getParameter("host"));
        return "handled";
    }
}
