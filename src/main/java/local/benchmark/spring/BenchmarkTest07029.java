package local.benchmark.spring;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/benchmark/brokensanit")
public class BenchmarkTest07029 {
    @GetMapping("/BenchmarkTest07029")
    public String run(@RequestParam String p) throws Exception {
        String clean = p.trim().toLowerCase();
   Runtime.getRuntime().exec("ping " + clean);
   return "ok";
    }
}
