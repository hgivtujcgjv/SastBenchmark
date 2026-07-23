package local.benchmark.spring;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/benchmark/cmdi-source")
public class BenchmarkTest06025 {
    @RequestMapping("/BenchmarkTest06025")
    public String run(HttpServletRequest request) throws java.io.IOException {
        String host = request.getParameter("host");
        Runtime.getRuntime().exec("echo " + host);
        return "ok";
    }
}
