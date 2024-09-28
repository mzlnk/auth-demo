package io.mzlnk.authdemo.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.mzlnk.authdemo.user.UserFacade;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@Configuration
class AuthModule {

    @Bean
    AuthFacade authFacade(
            @Value("${auth.jwt.private-key}") RSAPrivateKey privateKey,
            @Value("${auth.jwt.public-key}") RSAPublicKey publicKey,
            @Value("${auth.jwt.issuer}") String issuer,
            UserFacade userFacade
    ) {
        return new AuthFacade(
                /* userFacade = */ userFacade,
                /* authRepository = */ new InMemoryAuthRepository(),
                /* tokenGenerator = */ new TokenGenerator(privateKey, issuer),
                /* tokenVerifier = */ new TokenVerifier(publicKey, issuer)
        );
    }

    @Bean
    GoogleOAuth2Exchange googleOAuth2Exchange(
            @Value("${auth.google.client-id}") String clientId,
            @Value("${auth.google.client-secret}") String clientSecret,
            @Value("${auth.google.redirect-uri}") String redirectUri
    ) {
        return new GoogleOAuth2Exchange(
                /* httpClient = */ new OkHttpClient(),
                /* credentials = */ new GoogleOAuth2Exchange.Credentials(
                /* clientId = */ clientId,
                /* clientSecret = */ clientSecret,
                /* redirectUri = */ redirectUri
        ),
                /* objectMapper = */ new ObjectMapper()
        );
    }

}
