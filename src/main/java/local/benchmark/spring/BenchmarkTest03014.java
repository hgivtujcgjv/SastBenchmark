package local.benchmark.spring;

import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/benchmark/redirect-00")
public class BenchmarkTest03014 {
    private static final Set<String> ALLOWED_TARGETS = Set.of("/dashboard", "/orders", "/profile");

    @GetMapping("/BenchmarkTest03014")
    public String returnToAfterLogin(@RequestParam String next) {
        String target = ALLOWED_TARGETS.contains(next) ? next : "/";
        return "redirect:" + target;
    }
}
