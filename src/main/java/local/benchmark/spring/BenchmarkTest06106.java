package local.benchmark.spring;

import java.util.Set;
import org.springframework.web.bind.annotation.*;

 @RestController
 @RequestMapping("/benchmark/c-redirect2")
 public class BenchmarkTest06106 {
     private static final Set<String> ALLOWED = Set.of("/home", "/profile");

@GetMapping("/BenchmarkTest06106")
     public String run(@RequestParam String p) throws Exception {
         return ALLOWED.contains(p) ? "redirect:" + p : "redirect:/home";
     }
 }
