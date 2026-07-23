package local.benchmark.spring;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/benchmark/deadcode")
public class BenchmarkTest07050 {
    @GetMapping("/BenchmarkTest07050")
    public String run(@RequestParam String p) throws Exception {
        if (p != null) {
       Runtime.getRuntime().exec("ping " + p);
   }
   return "ok";
    }
}
