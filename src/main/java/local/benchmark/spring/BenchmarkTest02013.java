package local.benchmark.spring;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/benchmark/deserialization-00")
public class BenchmarkTest02013 {
    @PostMapping("/BenchmarkTest02013")
    public Object deserializeWithDefaultTyping(@RequestBody String json) throws Exception {
        BasicPolymorphicTypeValidator validator = BasicPolymorphicTypeValidator.builder()
                .allowIfSubType("local.benchmark.spring")
                .build();
        ObjectMapper mapper = new ObjectMapper();
        mapper.activateDefaultTyping(validator, ObjectMapper.DefaultTyping.NON_FINAL);
        return mapper.readValue(json, Object.class);
    }
}
