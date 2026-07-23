package local.benchmark.spring;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/benchmark/c-ssrf2")
public class BenchmarkTest06114 {
    @GetMapping("/BenchmarkTest06114")
    public String run(@RequestParam String p) throws Exception {
        HttpRequest req = HttpRequest.newBuilder(URI.create(p)).build();
   return HttpClient.newHttpClient().send(req, HttpResponse.BodyHandlers.ofString()).body();
    }
}
