package local.benchmark.spring;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/benchmark/brokensanit")
public class BenchmarkTest07025 {
    @GetMapping("/BenchmarkTest07025")
    public String run(@RequestParam String p) throws Exception {
        boolean valid = p.matches("^[a-z0-9]{1,32}$");
   Runtime.getRuntime().exec("ping " + p);
   return "ok";
    }
}
