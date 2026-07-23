package local.benchmark.spring;

import javax.naming.InitialContext;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/benchmark/jndi")
public class BenchmarkTest08045 {
    @GetMapping("/BenchmarkTest08045")
    public String lookup(@RequestParam String name) throws Exception {
        return new InitialContext().lookup("java:comp/env/jdbc/main").toString();
    }
}
