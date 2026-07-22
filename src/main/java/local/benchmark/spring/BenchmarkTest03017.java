package local.benchmark.spring;

import java.util.Locale;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@RestController
@RequestMapping("/benchmark/templateinj-00")
public class BenchmarkTest03017 {
    private final SpringTemplateEngine templateEngine;

    public BenchmarkTest03017(SpringTemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    @PostMapping("/BenchmarkTest03017")
    public String previewNotification(@RequestParam String template) {
        Context context = new Context(Locale.ENGLISH);
        context.setVariable("recipient", "preview@example.com");
        return templateEngine.process(template, context);
    }
}
