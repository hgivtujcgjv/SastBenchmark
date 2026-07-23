package local.benchmark.spring;

import java.util.Set;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/benchmark/cmdi-entry")
public class BenchmarkTest06014 {
    private static final Set<String> ALLOWED = Set.of("alpha", "beta", "gamma");

    @GetMapping("/BenchmarkTest06014")
    public String run(@RequestParam String host) throws java.io.IOException {
        if (!ALLOWED.contains(host)) {
            return "rejected";
        }
        Runtime.getRuntime().exec("echo " + host);
        return "ok";
    }
}
