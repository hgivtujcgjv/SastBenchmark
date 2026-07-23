package local.benchmark.spring;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/benchmark/lombok")
public class BenchmarkTest07010 {
    public static class HostDto {
        private String host;
        public String getHost() { return host; }
        public void setHost(String host) { this.host = host; }
    }

    @PostMapping("/BenchmarkTest07010")
    public String run(@RequestBody HostDto dto) throws java.io.IOException {
        Runtime.getRuntime().exec("echo " + dto.getHost());
        return "ok";
    }
}
