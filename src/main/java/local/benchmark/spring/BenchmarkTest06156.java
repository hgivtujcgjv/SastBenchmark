package local.benchmark.spring;

import java.util.Set;
import org.springframework.web.bind.annotation.*;

 @RestController
 @RequestMapping("/benchmark/c-templateinj2")
 public class BenchmarkTest06156 {
     private static final Set<String> VIEWS = Set.of("greeting", "farewell");

private final org.thymeleaf.spring6.SpringTemplateEngine engine;

public BenchmarkTest06156(org.thymeleaf.spring6.SpringTemplateEngine engine) {
    this.engine = engine;
}

@GetMapping("/BenchmarkTest06156")
     public String run(@RequestParam String p) throws Exception {
         if (!VIEWS.contains(p)) {
        return "rejected";
    }
    return engine.process(p, new org.thymeleaf.context.Context());
     }
 }
