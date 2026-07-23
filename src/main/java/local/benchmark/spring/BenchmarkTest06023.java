package local.benchmark.spring;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/benchmark/cmdi-source")
public class BenchmarkTest06023 {
    @RequestMapping("/BenchmarkTest06023")
    public String run(@CookieValue("host") String q) throws java.io.IOException {
        String host = q;
        Runtime.getRuntime().exec("echo " + host);
        return "ok";
    }
}
