package local.benchmark.spring;

import java.util.Map;

import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/benchmark/spel-00")
public class BenchmarkTest02022 {
    @GetMapping("/BenchmarkTest02022")
    public Object evaluateWithContext(@RequestParam String expression) {
        ExpressionParser parser = new SpelExpressionParser();
        StandardEvaluationContext context = new StandardEvaluationContext(Map.of("role", "USER"));
        return parser.parseExpression(expression).getValue(context);
    }
}
