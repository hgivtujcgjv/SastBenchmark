package local.benchmark.spring;

import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/benchmark/spel-00")
public class BenchmarkTest02024 {
    @GetMapping("/BenchmarkTest02024")
    public String evaluateFixedVariable(@RequestParam(defaultValue = "guest") String name) {
        ExpressionParser parser = new SpelExpressionParser();
        StandardEvaluationContext context = new StandardEvaluationContext();
        context.setVariable("name", name);
        return parser.parseExpression("#name").getValue(context, String.class);
    }
}
