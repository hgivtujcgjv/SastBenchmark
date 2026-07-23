package local.benchmark.spring;

import org.springframework.core.io.ResourceLoader;
import org.springframework.web.bind.annotation.*;

 @RestController
 @RequestMapping("/benchmark/c-pathtraver2")
 public class BenchmarkTest06133 {
     private final ResourceLoader loader;

public BenchmarkTest06133(ResourceLoader loader) {
    this.loader = loader;
}

@GetMapping("/BenchmarkTest06133")
     public String run(@RequestParam String p) throws Exception {
         return loader.getResource("file:/var/data/" + p).getFile().getAbsolutePath();
     }
 }
