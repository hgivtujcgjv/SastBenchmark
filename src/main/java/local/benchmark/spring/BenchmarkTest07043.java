package local.benchmark.spring;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/benchmark/deadcode")
public class BenchmarkTest07043 {
    @GetMapping("/BenchmarkTest07043")
    public String run(@RequestParam String p) {
        return "ok";
    }

    @SuppressWarnings("unused")
    private void neverCalled(String p) throws Exception {
        Runtime.getRuntime().exec("ping " + p);
    }
}
