package local.benchmark.spring;

import lombok.Builder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/benchmark/lombok")
public class BenchmarkTest07009 {
    @Builder
    public static class Command {
        private String host;
        public String render() { return "echo " + host; }
    }

    @GetMapping("/BenchmarkTest07009")
    public String run(@RequestParam String host) throws java.io.IOException {
        Command cmd = Command.builder().host(host).build();
        Runtime.getRuntime().exec(cmd.render());
        return "ok";
    }
}
