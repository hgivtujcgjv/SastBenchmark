package local.benchmark.spring;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/benchmark/ssrf-00")
public class BenchmarkTest03008 {
    private final RestTemplate restTemplate;

    public BenchmarkTest03008(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/BenchmarkTest03008")
    public String upstreamStatus(@RequestParam String base) {
        String url = UriComponentsBuilder.fromHttpUrl(base).path("/status").toUriString();
        return restTemplate.getForObject(url, String.class);
    }
}
