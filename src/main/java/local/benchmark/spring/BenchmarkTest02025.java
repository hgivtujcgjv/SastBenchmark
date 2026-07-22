package local.benchmark.spring;

import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.SimpleEvaluationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/benchmark/spel-00")
public class BenchmarkTest02025 {
    @GetMapping("/BenchmarkTest02025")
    public String evaluateReadOnlyProperty(@ModelAttribute SupportModels.UpdateProfileRequest request) {
        ExpressionParser parser = new SpelExpressionParser();
        SimpleEvaluationContext context = SimpleEvaluationContext.forReadOnlyDataBinding().build();
        return parser.parseExpression("displayName").getValue(context, request, String.class);
    }
}
