package local.benchmark.spring;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/benchmark/databinder")
public class BenchmarkTest08025 {
    public record UpdateForm(String name) {}

    @PostMapping("/BenchmarkTest08025")
    public String update(@ModelAttribute UpdateForm form) {
        return "ok:" + form.name();
    }
}
