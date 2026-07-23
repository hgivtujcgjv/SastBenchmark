package local.benchmark.spring;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/benchmark/brokensanit")
public class BenchmarkTest07026 {
    @GetMapping("/BenchmarkTest07026")
    public String run(@RequestParam String p) throws Exception {
        if ("check".equals(p)) {
       if (!p.matches("^[a-z]+$")) { return "rejected"; }
   }
   Runtime.getRuntime().exec("ping " + p);
   return "ok";
    }
}
