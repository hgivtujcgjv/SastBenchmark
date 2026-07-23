package local.benchmark.spring;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/benchmark/cmdi-source")
public class BenchmarkTest06035 {
    @RequestMapping("/BenchmarkTest06035")
    public String run(HttpServletRequest request) throws java.io.IOException {
        String host = request.getQueryString();
        if (host == null || !host.matches("^host=[a-z0-9]{1,32}$")) {
            return "rejected";
        }
        Runtime.getRuntime().exec("echo " + host);
        return "ok";
    }
}
