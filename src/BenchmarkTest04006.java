/*
 * OWASP Benchmark-style Java pilot case.
 */

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/benchmark/jwt-00/BenchmarkTest04006")
public class BenchmarkTest04006 extends HttpServlet {

    private static final Path PUBLIC_KEY_FILE = Paths.get("/etc/benchmark/jwt/auth-service.pub");
    private static final String EXPECTED_ISSUER = "https://auth.benchmark.example";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String token = bearerToken(request.getHeader("Authorization"));
        Algorithm pinnedAlgorithm = Algorithm.RSA256(loadAuthServicePublicKey());

        try {
            DecodedJWT verified = JWT.require(pinnedAlgorithm)
                    .withIssuer(EXPECTED_ISSUER)
                    .build()
                    .verify(token);

            response.getWriter().println("role=" + verified.getClaim("role").asString());
        } catch (JWTVerificationException rejected) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "invalid token");
        }
    }

    private static RSAPublicKey loadAuthServicePublicKey() throws IOException {
        String pem = new String(Files.readAllBytes(PUBLIC_KEY_FILE), StandardCharsets.UTF_8);
        String body = pem.replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s", "");
        try {
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(body));
            return (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(keySpec);
        } catch (GeneralSecurityException unusableKey) {
            throw new IOException("unusable JWT verification key", unusableKey);
        }
    }

    private static String bearerToken(String authorization) {
        return authorization == null ? "" : authorization.replace("Bearer ", "");
    }
}
