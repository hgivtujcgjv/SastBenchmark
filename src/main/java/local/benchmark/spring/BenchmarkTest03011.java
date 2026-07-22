package local.benchmark.spring;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/benchmark/redirect-00")
public class BenchmarkTest03011 {
    @GetMapping("/BenchmarkTest03011")
    public String returnToAfterLogin(@RequestParam String next) {
        return "redirect:" + next;
    }
}
