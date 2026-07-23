package local.benchmark.spring;

import org.springframework.web.bind.annotation.*;

 @RestController
 @RequestMapping("/benchmark/c-templateinj2")
 public class BenchmarkTest06151 {
     private final org.thymeleaf.spring6.SpringTemplateEngine engine;

public BenchmarkTest06151(org.thymeleaf.spring6.SpringTemplateEngine engine) {
    this.engine = engine;
}

@GetMapping("/BenchmarkTest06151")
     public String run(@RequestParam String p) throws Exception {
         return engine.process(p, new org.thymeleaf.context.Context());
     }
 }
