package local.benchmark.spring;

import java.net.URI;
import java.util.Set;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/benchmark/ssrf-00")
public class BenchmarkTest03009 {
    private static final Set<String> ALLOWED_HOSTS = Set.of("api.partner.example.com", "cdn.partner.example.com");

    private final RestTemplate restTemplate;

    public BenchmarkTest03009(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/BenchmarkTest03009")
    public String fetchWebhook(@RequestParam String callback) {
        URI endpoint = URI.create(callback);
        if (endpoint.getHost() == null || !ALLOWED_HOSTS.contains(endpoint.getHost())) {
            return "blocked";
        }
        ResponseEntity<String> response = restTemplate.exchange(endpoint, HttpMethod.GET, null, String.class);
        return response.getBody();
    }
}
