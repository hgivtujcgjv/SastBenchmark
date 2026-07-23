package local.benchmark.spring;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/benchmark/brokensanit")
public class BenchmarkTest07021 {
    @GetMapping("/BenchmarkTest07021")
    public String run(@RequestParam String p) throws Exception {
        if (p.contains("..") || p.contains("&")) {
       return "rejected";
   }
   Runtime.getRuntime().exec("ping " + p);
   return "ok";
    }
}
