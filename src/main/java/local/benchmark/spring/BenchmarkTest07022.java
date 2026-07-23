package local.benchmark.spring;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/benchmark/brokensanit")
public class BenchmarkTest07022 {
    @GetMapping("/BenchmarkTest07022")
    public String run(@RequestParam String p) throws Exception {
        if (!p.matches(".*[a-z]+.*")) {
       return "rejected";
   }
   Runtime.getRuntime().exec("ping " + p);
   return "ok";
    }
}
