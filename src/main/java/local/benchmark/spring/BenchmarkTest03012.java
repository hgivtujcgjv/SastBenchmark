package local.benchmark.spring;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/benchmark/redirect-00")
public class BenchmarkTest03012 {
    @GetMapping("/BenchmarkTest03012")
    public RedirectView resumeSession(@CookieValue("returnTo") String returnTo) {
        return new RedirectView(returnTo);
    }
}
