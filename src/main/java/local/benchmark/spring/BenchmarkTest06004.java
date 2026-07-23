package local.benchmark.spring;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/benchmark/cmdi-entry")
public class BenchmarkTest06004 {
    @GetMapping("/BenchmarkTest06004/{host}")
    public String run(@PathVariable String host) throws java.io.IOException {
        Runtime.getRuntime().exec("echo " + host);
        return "ok";
    }
}
