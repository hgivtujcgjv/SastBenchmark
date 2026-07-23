package local.benchmark.spring;

import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.SimpleEvaluationContext;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/benchmark/c-spel2")
public class BenchmarkTest06145 {
    @GetMapping("/BenchmarkTest06145")
    public String run(@RequestParam String p) throws Exception {
        var ctx = SimpleEvaluationContext.forReadOnlyDataBinding().build();
   return String.valueOf(new SpelExpressionParser()
           .parseExpression("#root").getValue(ctx, p));
    }
}
