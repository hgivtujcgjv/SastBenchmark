package local.benchmark.spring;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/benchmark/c-redirect2")
public class BenchmarkTest06107 {
    @GetMapping("/BenchmarkTest06107")
    public String run(@RequestParam String p) throws Exception {
        return "redirect:/local/" + p.replaceAll("[^a-z0-9]", "");
    }
}
