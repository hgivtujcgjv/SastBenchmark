package local.benchmark.spring;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/benchmark/c-deser2")
public class BenchmarkTest06176 {
    @GetMapping("/BenchmarkTest06176")
    public String run(@RequestParam String p) throws Exception {
        return String.valueOf(new ObjectMapper().readValue(p, java.util.Map.class));
    }
}
