package local.benchmark.spring;

import java.util.Set;
import javax.naming.InitialContext;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/benchmark/jndi")
public class BenchmarkTest08044 {
    private static final Set<String> ALLOWED = Set.of("java:comp/env/jdbc/main");

    @GetMapping("/BenchmarkTest08044")
    public String lookup(@RequestParam String name) throws Exception {
        if (!ALLOWED.contains(name)) {
            return "rejected";
        }
        return new InitialContext().lookup(name).toString();
    }
}
