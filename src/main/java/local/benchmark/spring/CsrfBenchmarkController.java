package local.benchmark.spring;

import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/benchmark/csrf-00")
public class CsrfBenchmarkController {
    @PostMapping({
            "/BenchmarkTest02016/transfer",
            "/BenchmarkTest02017/transfer",
            "/BenchmarkTest02018/transfer",
            "/BenchmarkTest02019/transfer",
            "/BenchmarkTest02020/transfer"
    })
    public Map<String, String> transfer() {
        return Map.of("status", "updated");
    }
}
