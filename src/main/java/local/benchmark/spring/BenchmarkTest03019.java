package local.benchmark.spring;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/benchmark/templateinj-00")
public class BenchmarkTest03019 {
    private static final Map<String, String> SECTION_VIEWS = Map.of(
            "overview", "reports/overview",
            "billing", "reports/billing",
            "audit", "reports/audit");

    @GetMapping("/BenchmarkTest03019")
    public String renderSection(@RequestParam String section) {
        return SECTION_VIEWS.getOrDefault(section, "reports/overview");
    }
}
