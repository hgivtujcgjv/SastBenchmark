/*
 * OWASP Benchmark-style Java pilot case.
 */

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/benchmark/jwt-00/BenchmarkTest01267")
public class BenchmarkTest01267 extends HttpServlet {
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
        String jku = decoded.getHeaderClaim("jku").asString();

        String remoteKey = new Scanner(new URL(jku).openStream(), StandardCharsets.UTF_8.name()).useDelimiter("\\A").next();
        DecodedJWT verified = JWT.require(Algorithm.HMAC256(remoteKey)).build().verify(token);

        response.getWriter().println("issuer=" + verified.getIssuer());
    }

    private static String bearerToken(String authorization) {
        return authorization == null ? "" : authorization.replace("Bearer ", "");
    }
}

