package local.benchmark.spring;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/benchmark/deserialization-00")
public class BenchmarkTest02014 {
    private final ObjectMapper objectMapper;

    public BenchmarkTest02014(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @PostMapping("/BenchmarkTest02014")
    public SupportModels.SafePayload parseTypedJson(@RequestBody String json) throws Exception {
        return objectMapper.readValue(json, SupportModels.SafePayload.class);
    }
}
