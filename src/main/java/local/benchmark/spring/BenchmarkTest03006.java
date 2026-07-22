package local.benchmark.spring;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/benchmark/ssrf-00")
public class BenchmarkTest03006 {
    @GetMapping("/BenchmarkTest03006")
    public String previewLink(@RequestParam String target) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(target, String.class);
    }
}
