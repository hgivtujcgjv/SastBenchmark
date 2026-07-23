package local.benchmark.spring;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/benchmark/c-redirect2")
public class BenchmarkTest06101 {
    @GetMapping("/BenchmarkTest06101")
    public String run(@RequestParam String p) throws Exception {
        return "redirect:" + p;
    }
}
