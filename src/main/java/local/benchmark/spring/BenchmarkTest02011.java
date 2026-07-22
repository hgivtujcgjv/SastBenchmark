package local.benchmark.spring;

import java.io.ObjectInputStream;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/benchmark/deserialization-00")
public class BenchmarkTest02011 {
    @PostMapping("/BenchmarkTest02011")
    public String deserializeFromRequest(HttpServletRequest request) throws Exception {
        try (ObjectInputStream stream = new ObjectInputStream(request.getInputStream())) {
            Object value = stream.readObject();
            return String.valueOf(value);
        }
    }
}
