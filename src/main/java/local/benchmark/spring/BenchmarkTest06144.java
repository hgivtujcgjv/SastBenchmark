package local.benchmark.spring;

import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/benchmark/c-spel2")
public class BenchmarkTest06144 {
    @GetMapping("/BenchmarkTest06144")
    public String run(@RequestParam String p) throws Exception {
        ExpressionParser parser = new SpelExpressionParser();
   return String.valueOf(parser.parseExpression("T(java.lang.String).valueOf(" + p + ")")
           .getValue());
    }
}
