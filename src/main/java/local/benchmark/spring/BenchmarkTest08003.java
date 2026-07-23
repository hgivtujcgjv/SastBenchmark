package local.benchmark.spring;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/benchmark/jwt")
public class BenchmarkTest08003 {
    private static final String SECRET = "secret123";

    @GetMapping("/BenchmarkTest08003")
    public String verify(@RequestHeader("Authorization") String auth) throws Exception {
        String[] p = auth.replace("Bearer ", "").split("\\.");
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(SECRET.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
        String sig = Base64.getUrlEncoder().withoutPadding()
                .encodeToString(mac.doFinal((p[0] + "." + p[1]).getBytes(StandardCharsets.UTF_8)));
        return sig.equals(p[2]) ? new String(Base64.getUrlDecoder().decode(p[1])) : "denied";
    }
}
