package local.benchmark.spring;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/benchmark/deadcode")
public class BenchmarkTest07042 {
    @GetMapping("/BenchmarkTest07042")
    @SuppressWarnings("unused")
    public String run(@RequestParam String p) throws Exception {
        if (true) {
            return "ok";
        }
        Runtime.getRuntime().exec("ping " + p);
        return "unreachable";
    }
}
