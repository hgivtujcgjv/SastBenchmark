package local.benchmark.spring;

import java.net.URI;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/benchmark/ssrf-00")
public class BenchmarkTest03007 {
    private final RestTemplate restTemplate;

    public BenchmarkTest03007(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/BenchmarkTest03007")
    public String fetchWebhook(@RequestParam String callback) {
        URI endpoint = URI.create(callback);
        ResponseEntity<String> response = restTemplate.exchange(endpoint, HttpMethod.GET, null, String.class);
        return response.getBody();
    }
}
