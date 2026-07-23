package local.benchmark.spring;

import java.util.Set;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.web.bind.annotation.*;

 @RestController
 @RequestMapping("/benchmark/c-spel2")
 public class BenchmarkTest06146 {
     private static final Set<String> EXPR = Set.of("1 + 1", "2 * 2");

@GetMapping("/BenchmarkTest06146")
     public String run(@RequestParam String p) throws Exception {
         if (!EXPR.contains(p)) {
        return "rejected";
    }
    return String.valueOf(new SpelExpressionParser().parseExpression(p).getValue());
     }
 }
