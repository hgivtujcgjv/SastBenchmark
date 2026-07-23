package local.benchmark.spring;

import java.net.URI;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/benchmark/c-redirect2")
public class BenchmarkTest06108 {
    @GetMapping("/BenchmarkTest06108")
    public String run(@RequestParam String p) throws Exception {
        URI u = URI.create(p);
   if (u.isAbsolute()) {
       return "redirect:/home";
   }
   return "redirect:" + u.getPath();
    }
}
