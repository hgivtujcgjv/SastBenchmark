package local.benchmark.spring;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/benchmark/c-redirect2")
public class BenchmarkTest06102 {
    @GetMapping("/BenchmarkTest06102")
    public RedirectView run(@RequestParam String p) {
        return new RedirectView(p);
    }
}
