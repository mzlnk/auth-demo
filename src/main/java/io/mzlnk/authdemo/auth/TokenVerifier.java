package io.mzlnk.authdemo.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;

import java.security.interfaces.RSAPublicKey;

class TokenVerifier {

    private final JWTVerifier verifier;

    TokenVerifier(RSAPublicKey publicKey, String issuer) {
        this.verifier = JWT.require(Algorithm.RSA512(publicKey, null))
                .withIssuer(issuer)
                .build();
    }

    boolean verifyToken(Token token) {
        try {
            verifier.verify(token.value());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
