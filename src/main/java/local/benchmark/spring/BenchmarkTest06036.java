package local.benchmark.spring;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/benchmark/cmdi-source")
public class BenchmarkTest06036 {
    @RequestMapping("/BenchmarkTest06036")
    public String run(@RequestParam String q) throws java.io.IOException {
        String host = q.replaceAll("[^a-zA-Z0-9]", "");
        Runtime.getRuntime().exec("echo " + host);
        return "ok";
    }
}
