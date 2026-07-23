package local.benchmark.spring;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/benchmark/brokensanit")
public class BenchmarkTest07030 {
    @GetMapping("/BenchmarkTest07030")
    public String run(@RequestParam String p) throws Exception {
        if (p.length() > 64) {
       return "rejected";
   }
   Runtime.getRuntime().exec("ping " + p);
   return "ok";
    }
}
