package local.benchmark.spring;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/benchmark/jwt")
public class BenchmarkTest08006 {
    @Value("${jwt.secret}")
    private String secret;

    @GetMapping("/BenchmarkTest08006")
    public String verify(@RequestHeader("Authorization") String auth) throws Exception {
        String[] p = auth.replace("Bearer ", "").split("\\.");
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
        String sig = Base64.getUrlEncoder().withoutPadding()
                .encodeToString(mac.doFinal((p[0] + "." + p[1]).getBytes(StandardCharsets.UTF_8)));
        if (!sig.equals(p[2])) {
            return "denied";
        }
        return new String(Base64.getUrlDecoder().decode(p[1]));
    }
}
