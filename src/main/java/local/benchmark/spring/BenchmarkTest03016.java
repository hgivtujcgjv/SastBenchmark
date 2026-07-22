package local.benchmark.spring;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/benchmark/templateinj-00")
public class BenchmarkTest03016 {
    @GetMapping("/BenchmarkTest03016")
    public String renderSection(@RequestParam String section) {
        return section;
    }
}
