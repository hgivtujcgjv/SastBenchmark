package local.benchmark.spring;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/benchmark/cmdi-source")
public class BenchmarkTest06021 {
    @RequestMapping("/BenchmarkTest06021")
    public String run(@RequestParam String q) throws java.io.IOException {
        String host = q;
        Runtime.getRuntime().exec("echo " + host);
        return "ok";
    }
}
