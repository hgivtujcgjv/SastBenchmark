package local.benchmark.spring;

import org.springframework.web.bind.annotation.*;

 @RestController
 @RequestMapping("/benchmark/c-templateinj2")
 public class BenchmarkTest06155 {
     private final org.thymeleaf.spring6.SpringTemplateEngine engine;

public BenchmarkTest06155(org.thymeleaf.spring6.SpringTemplateEngine engine) {
    this.engine = engine;
}

@GetMapping("/BenchmarkTest06155")
     public String run(@RequestParam String p) throws Exception {
         var ctx = new org.thymeleaf.context.Context();
    ctx.setVariable("name", p);
    return engine.process("greeting", ctx);
     }
 }
