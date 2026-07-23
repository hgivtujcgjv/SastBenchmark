package local.benchmark.spring;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/benchmark/cmdi-entry")
public class BenchmarkTest06001 {
    @GetMapping("/BenchmarkTest06001")
    public String run(@RequestParam String host) throws java.io.IOException {
        Runtime.getRuntime().exec("echo " + host);
        return "ok";
    }
}
