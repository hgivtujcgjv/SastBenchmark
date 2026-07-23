package local.benchmark.spring;

import javax.naming.InitialContext;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/benchmark/jndi")
public class BenchmarkTest08043 {
    @PostMapping("/BenchmarkTest08043")
    public String bind(@RequestParam String name, @RequestParam String ref) throws Exception {
        InitialContext ctx = new InitialContext();
        Object obj = ctx.lookup(ref);
        ctx.rebind(name, obj);
        return "bound";
    }
}
