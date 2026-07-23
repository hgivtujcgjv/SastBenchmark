package local.benchmark.spring;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/benchmark/cmdi-springsrc")
public class BenchmarkTest09014 {
    @RequestMapping("/BenchmarkTest09014")
    public String run(HttpServletRequest request) throws Exception {
        String host = request.getPathInfo();
        Runtime.getRuntime().exec("echo " + host);
        return "ok";
    }
}
