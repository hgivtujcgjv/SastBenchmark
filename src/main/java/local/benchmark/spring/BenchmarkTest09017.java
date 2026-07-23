package local.benchmark.spring;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/benchmark/cmdi-springsrc")
public class BenchmarkTest09017 {
    @RequestMapping("/BenchmarkTest09017")
    public String run(@RequestPart("f") MultipartFile file) throws Exception {
        String host = file.getOriginalFilename();
        Runtime.getRuntime().exec("echo " + host);
        return "ok";
    }
}
