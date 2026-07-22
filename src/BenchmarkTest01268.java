/*
 * OWASP Benchmark-style Java pilot case.
 */

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/benchmark/jwt-00/BenchmarkTest01268")
public class BenchmarkTest01268 extends HttpServlet {
    private static final Path KEY_DIRECTORY = Paths.get("/tmp/benchmark-java-jwt-keys");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String token = bearerToken(request.getHeader("Authorization"));
        DecodedJWT decoded = JWT.decode(token);
        String kid = defaultValue(decoded.getKeyId(), "default.key");
        Path keyPath = KEY_DIRECTORY.resolve(kid);

        String verificationKey = new String(Files.readAllBytes(keyPath), StandardCharsets.UTF_8);
        DecodedJWT verified = JWT.require(Algorithm.HMAC256(verificationKey)).build().verify(token);

        response.getWriter().println("user=" + verified.getSubject());
    }

    private static String bearerToken(String authorization) {
        return authorization == null ? "" : authorization.replace("Bearer ", "");
    }

    private static String defaultValue(String value, String fallback) {
        return value == null ? fallback : value;
    }
}

