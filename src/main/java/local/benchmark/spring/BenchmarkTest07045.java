package local.benchmark.spring;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/benchmark/deadcode")
public class BenchmarkTest07045 {
    @GetMapping("/BenchmarkTest07045")
    public String run(@RequestParam String p) throws Exception {
        int x = 1;
   if (x == 2) {
       Runtime.getRuntime().exec("ping " + p);
   }
   return "ok";
    }
}
