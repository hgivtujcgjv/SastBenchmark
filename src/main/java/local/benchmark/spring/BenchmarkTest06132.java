package local.benchmark.spring;

import org.springframework.core.io.FileSystemResource;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/benchmark/c-pathtraver2")
public class BenchmarkTest06132 {
    @GetMapping("/BenchmarkTest06132")
    public String run(@RequestParam String p) throws Exception {
        return new FileSystemResource("/var/data/" + p).getFile().getAbsolutePath();
    }
}
