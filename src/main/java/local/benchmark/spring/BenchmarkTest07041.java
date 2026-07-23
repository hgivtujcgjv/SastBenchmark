package local.benchmark.spring;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/benchmark/deadcode")
public class BenchmarkTest07041 {
    @GetMapping("/BenchmarkTest07041")
    public String run(@RequestParam String p) throws Exception {
        if (false) {
       Runtime.getRuntime().exec("ping " + p);
   }
   return "ok";
    }
}
