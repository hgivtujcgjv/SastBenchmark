package local.benchmark.spring;

import javax.naming.InitialContext;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/benchmark/jndi")
public class BenchmarkTest08041 {
    @GetMapping("/BenchmarkTest08041")
    public String lookup(@RequestParam String name) throws Exception {
        return new InitialContext().lookup(name).toString();
    }
}
