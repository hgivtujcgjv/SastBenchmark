package local.benchmark.spring;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/benchmark/deadcode")
public class BenchmarkTest07046 {
    @GetMapping("/BenchmarkTest07046")
    @SuppressWarnings("unused")
    public String run(@RequestParam String p) throws Exception {
        if (true) {
            throw new IllegalStateException("disabled");
        }
        Runtime.getRuntime().exec("ping " + p);
        return "unreachable";
    }
}
