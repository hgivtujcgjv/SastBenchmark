package local.benchmark.spring;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/benchmark/c-templateinj2")
public class BenchmarkTest06152 {
    @GetMapping("/BenchmarkTest06152")
    public String run(@RequestParam String p) {
        return p;
    }
}
