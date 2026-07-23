package local.benchmark.spring;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/benchmark/deadcode")
public class BenchmarkTest07044 {
    private static final boolean DEBUG = false;

    @GetMapping("/BenchmarkTest07044")
    public String run(@RequestParam String p) throws Exception {
        if (DEBUG) {
            Runtime.getRuntime().exec("ping " + p);
        }
        return "ok";
    }
}
