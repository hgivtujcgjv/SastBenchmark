package local.benchmark.spring;

import java.util.regex.Pattern;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.util.UriComponentsBuilder;

@Controller
@RequestMapping("/benchmark/redirect-00")
public class BenchmarkTest03015 {
    private static final Pattern RELATIVE_PATH = Pattern.compile("/[A-Za-z0-9/_-]{0,64}");

    @GetMapping("/BenchmarkTest03015")
    public RedirectView openInPortal(@RequestParam String path) {
        String requested = RELATIVE_PATH.matcher(path).matches() ? path : "/";
        String target = UriComponentsBuilder.newInstance()
                .scheme("https")
                .host("portal.internal.example.com")
                .path(requested)
                .toUriString();
        return new RedirectView(target);
    }
}
