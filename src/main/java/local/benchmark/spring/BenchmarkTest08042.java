package local.benchmark.spring;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/benchmark/jndi")
public class BenchmarkTest08042 {
    @GetMapping("/BenchmarkTest08042")
    public String resolve(@RequestParam String jndi) throws Exception {
        var ctx = new javax.naming.directory.InitialDirContext();
        return ctx.lookup(jndi).toString();
    }
}
