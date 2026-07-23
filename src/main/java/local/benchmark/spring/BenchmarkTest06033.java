package local.benchmark.spring;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/benchmark/cmdi-source")
public class BenchmarkTest06033 {
    @RequestMapping("/BenchmarkTest06033")
    public String run(@RequestParam String q) throws java.io.IOException {
        String host = "localhost";
        Runtime.getRuntime().exec("echo " + host);
        return "ok";
    }
}
