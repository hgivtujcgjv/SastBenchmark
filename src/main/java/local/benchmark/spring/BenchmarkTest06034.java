package local.benchmark.spring;

import java.util.Set;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/benchmark/cmdi-source")
public class BenchmarkTest06034 {
    private static final Set<String> ALLOWED = Set.of("alpha", "beta");

    @RequestMapping("/BenchmarkTest06034")
    public String run(@RequestParam String q) throws java.io.IOException {
        if (!ALLOWED.contains(q)) {
            return "rejected";
        }
        Runtime.getRuntime().exec("echo " + q);
        return "ok";
    }
}
