package local.benchmark.spring;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.util.Base64;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/benchmark/deserialization-00")
public class BenchmarkTest02012 {
    @PostMapping("/BenchmarkTest02012")
    public String deserializeFromBase64(@RequestBody String body) throws Exception {
        byte[] serialized = Base64.getDecoder().decode(body);
        try (ObjectInputStream stream = new ObjectInputStream(new ByteArrayInputStream(serialized))) {
            Object value = stream.readObject();
            return String.valueOf(value);
        }
    }
}
