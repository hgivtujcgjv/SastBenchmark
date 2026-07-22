package local.benchmark.spring;

import java.util.regex.Pattern;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/benchmark/ssrf-00")
public class BenchmarkTest03010 {
    private static final String BASE_URL = "https://api.partner.example.com/services/";
    private static final Pattern SERVICE_NAME = Pattern.compile("^[a-z0-9-]{1,32}$");

    private final RestTemplate restTemplate;

    public BenchmarkTest03010(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/BenchmarkTest03010")
    public String upstreamStatus(@RequestParam String service) {
        if (!SERVICE_NAME.matcher(service).matches()) {
            return "blocked";
        }
        return restTemplate.getForObject(BASE_URL + service + "/status", String.class);
    }
}
