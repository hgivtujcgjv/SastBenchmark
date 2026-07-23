package local.benchmark.spring;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/benchmark/cmdi-entry")
public class BenchmarkTest06012 {
    @PostMapping("/BenchmarkTest06012")
    public String run(@RequestPart("file") MultipartFile file) throws java.io.IOException {
        Runtime.getRuntime().exec("echo " + file.getOriginalFilename());
        return "ok";
    }
}
