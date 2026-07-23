package local.benchmark.spring;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/benchmark/deadcode")
public class BenchmarkTest07047 {
    @Value("${feature.enabled:false}")
    private boolean enabled;

    @GetMapping("/BenchmarkTest07047")
    public String run(@RequestParam String p) throws Exception {
        if (enabled && "impossible".equals("x")) {
            Runtime.getRuntime().exec("ping " + p);
        }
        return "ok";
    }
}
