package local.benchmark.spring;

import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/benchmark/c-spel2")
public class BenchmarkTest06142 {
    @GetMapping("/BenchmarkTest06142")
    public String run(@RequestParam String p) throws Exception {
        var ctx = new StandardEvaluationContext(new java.util.HashMap<>());
   return String.valueOf(new SpelExpressionParser().parseExpression(p).getValue(ctx));
    }
}
