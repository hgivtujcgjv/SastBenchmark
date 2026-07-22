package local.benchmark.spring;

import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/benchmark/spel-00")
public class BenchmarkTest02023 {
    @GetMapping("/BenchmarkTest02023")
    public Object evaluateConcatenatedExpression(@RequestParam String operand) {
        ExpressionParser parser = new SpelExpressionParser();
        String expression = "T(java.lang.Math).max(" + operand + ", 10)";
        return parser.parseExpression(expression).getValue();
    }
}
