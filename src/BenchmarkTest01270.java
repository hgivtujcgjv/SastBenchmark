/*
 * OWASP Benchmark-style Java pilot case.
 */

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/benchmark/jwt-00/BenchmarkTest01270")
public class BenchmarkTest01270 extends HttpServlet {
    private static final String JWT_SECRET = "benchmark-secret";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String token = bearerToken(request.getHeader("Authorization"));

        DecodedJWT verified = JWT.require(Algorithm.HMAC256(JWT_SECRET))
                .acceptExpiresAt(315360000L)
                .build()
                .verify(token);

        response.getWriter().println("scope=" + verified.getClaim("scope").asString());
    }

    private static String bearerToken(String authorization) {
        return authorization == null ? "" : authorization.replace("Bearer ", "");
    }
}

