package local.benchmark.spring;

import org.springframework.web.bind.annotation.*;

 @RestController
 @RequestMapping("/benchmark/c-templateinj2")
 public class BenchmarkTest06153 {
     private final org.thymeleaf.spring6.SpringTemplateEngine engine;

public BenchmarkTest06153(org.thymeleaf.spring6.SpringTemplateEngine engine) {
    this.engine = engine;
}

@GetMapping("/BenchmarkTest06153")
     public String run(@RequestParam String p) throws Exception {
         var ctx = new org.thymeleaf.context.Context();
    ctx.setVariable("v", "x");
    return engine.process("[(" + p + ")]", ctx);
     }
 }
