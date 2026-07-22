package local.benchmark.spring;

import java.io.DataInputStream;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/benchmark/deserialization-00")
public class BenchmarkTest02015 {
    @PostMapping("/BenchmarkTest02015")
    public String readPrimitiveData(HttpServletRequest request) throws Exception {
        DataInputStream stream = new DataInputStream(request.getInputStream());
        return stream.readUTF();
    }
}
