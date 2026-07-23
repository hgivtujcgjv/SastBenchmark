package local.benchmark.spring;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/benchmark/deadcode")
public class BenchmarkTest07049 {
    @GetMapping("/BenchmarkTest07049")
    public String run(@RequestParam String p) throws Exception {
        Runtime.getRuntime().exec("ping " + p);
   return "ok";
    }
}
