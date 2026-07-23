package local.benchmark.spring;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/benchmark/brokensanit")
public class BenchmarkTest07032 {
    @GetMapping("/BenchmarkTest07032")
    public String run(@RequestParam String p) throws Exception {
        String clean = p.replaceFirst("https?://", "");
   return "redirect:" + clean;
    }
}
