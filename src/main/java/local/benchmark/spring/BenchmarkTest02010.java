package local.benchmark.spring;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/benchmark/router-00")
public class BenchmarkTest02010 {
    private final Path baseDirectory = Paths.get("/tmp/spring-benchmark-static").toAbsolutePath().normalize();

    @GetMapping("/BenchmarkTest02010/files")
    public ResponseEntity<?> readFile(@RequestParam String name) {
        Path requested = baseDirectory.resolve(name).normalize();
        if (!requested.startsWith(baseDirectory)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("blocked");
        }
        return ResponseEntity.ok(new FileSystemResource(requested.toFile()));
    }
}
