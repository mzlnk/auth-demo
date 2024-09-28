package io.mzlnk.authdemo.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.security.interfaces.RSAPrivateKey;

class TokenGenerator {

    private final Algorithm algorithm;
    private final String issuer;

    TokenGenerator(RSAPrivateKey privateKey, String issuer) {
        this.algorithm = Algorithm.RSA512(null, privateKey);
        this.issuer = issuer;
    }

    Token generateToken(AuthUser user) {
        var token = JWT.create()
                .withIssuer(issuer)
                .withSubject(user.userId().value())
                .sign(algorithm);

        return new Token(token);
    }

}
