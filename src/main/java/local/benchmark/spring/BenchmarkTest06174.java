package local.benchmark.spring;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/benchmark/c-deser2")
public class BenchmarkTest06174 {
    @GetMapping("/BenchmarkTest06174")
    public String run(@RequestParam String p) throws Exception {
        var m = new ObjectMapper();
   m.activateDefaultTyping(LaissezFaireSubTypeValidator.instance,
           ObjectMapper.DefaultTyping.NON_FINAL);
   return String.valueOf(m.readValue(p, Object.class));
    }
}
