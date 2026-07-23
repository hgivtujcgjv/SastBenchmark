package local.benchmark.spring;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/benchmark/cmdi-source")
public class BenchmarkTest06032 {
    @RequestMapping("/BenchmarkTest06032")
    public String run(HttpServletRequest request) throws java.io.IOException {
        String host = new String(request.getInputStream().readAllBytes());
        Runtime.getRuntime().exec("echo " + host);
        return "ok";
    }
}
