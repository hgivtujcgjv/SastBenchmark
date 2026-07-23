package local.benchmark.spring;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/benchmark/cmdi-springsrc")
public class BenchmarkTest09015 {
    @RequestMapping("/BenchmarkTest09015")
    public String run(HttpServletRequest request) throws Exception {
        String host = request.getRequestURL().toString();
        Runtime.getRuntime().exec("echo " + host);
        return "ok";
    }
}
