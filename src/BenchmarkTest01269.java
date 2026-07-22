/*
 * OWASP Benchmark-style Java pilot case.
 */

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.io.IOException;
import java.security.interfaces.RSAPublicKey;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/benchmark/jwt-00/BenchmarkTest01269")
public class BenchmarkTest01269 extends HttpServlet {
    private static final String PUBLIC_KEY_PEM = "-----BEGIN PUBLIC KEY-----\nMIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8A\n-----END PUBLIC KEY-----";

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
        Algorithm algorithm;

        if ("HS256".equals(decoded.getAlgorithm())) {
            algorithm = Algorithm.HMAC256(PUBLIC_KEY_PEM);
        } else {
            algorithm = Algorithm.RSA256((RSAPublicKey) null, null);
        }

        DecodedJWT verified = JWT.require(algorithm).build().verify(token);
        response.getWriter().println("role=" + verified.getClaim("role").asString());
    }

    private static String bearerToken(String authorization) {
        return authorization == null ? "" : authorization.replace("Bearer ", "");
    }
}

