package local.benchmark.spring;

import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/benchmark/c-spel2")
public class BenchmarkTest06141 {
    @GetMapping("/BenchmarkTest06141")
    public String run(@RequestParam String p) throws Exception {
        return String.valueOf(new SpelExpressionParser().parseExpression(p).getValue());
    }
}
