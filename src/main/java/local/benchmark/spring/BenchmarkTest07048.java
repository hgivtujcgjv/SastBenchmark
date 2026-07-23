package local.benchmark.spring;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/benchmark/deadcode")
public class BenchmarkTest07048 {
    @GetMapping("/BenchmarkTest07048")
    public String run(@RequestParam String p) throws Exception {
        String cmd = "ping " + p;
   cmd = "ping localhost";
   Runtime.getRuntime().exec(cmd);
   return "ok";
    }
}
