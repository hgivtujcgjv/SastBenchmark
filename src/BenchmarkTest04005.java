/*
 * OWASP Benchmark-style Java pilot case.
 */

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/benchmark/jwt-00/BenchmarkTest04005")
public class BenchmarkTest04005 extends HttpServlet {

    private static final String EXPECTED_ISSUER = "https://auth.benchmark.example";
    private static final String EXPECTED_AUDIENCE = "benchmark-api";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String token = bearerToken(request.getHeader("Authorization"));
        String signingSecret = System.getenv("JWT_SIGNING_SECRET");
        if (signingSecret == null || signingSecret.isEmpty()) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "signing secret not configured");
            return;
        }

        try {
            DecodedJWT verified = JWT.require(Algorithm.HMAC256(signingSecret))
                    .withIssuer(EXPECTED_ISSUER)
                    .withAudience(EXPECTED_AUDIENCE)
                    .build()
                    .verify(token);

            response.getWriter().println("user=" + verified.getSubject());
        } catch (JWTVerificationException rejected) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "invalid token");
        }
    }

    private static String bearerToken(String authorization) {
        return authorization == null ? "" : authorization.replace("Bearer ", "");
    }
}
