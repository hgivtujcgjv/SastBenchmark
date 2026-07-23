package local.benchmark.spring;

import jakarta.servlet.ServletContext;
import org.springframework.web.bind.annotation.*;

 @RestController
 @RequestMapping("/benchmark/c-pathtraver2")
 public class BenchmarkTest06135 {
     private final ServletContext ctx;

public BenchmarkTest06135(ServletContext ctx) {
    this.ctx = ctx;
}

@GetMapping("/BenchmarkTest06135")
     public String run(@RequestParam String p) throws Exception {
         return ctx.getRealPath(p);
     }
 }
