package io.mzlnk.authdemo.auth;

import com.auth0.jwt.JWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import java.io.IOException;

public class GoogleOAuth2Exchange {

    private final OkHttpClient httpClient;
    private final Credentials credentials;
    private final ObjectMapper objectMapper;

    GoogleOAuth2Exchange(OkHttpClient httpClient,
                         GoogleOAuth2Exchange.Credentials credentials,
                         ObjectMapper objectMapper
    ) {
        this.httpClient = httpClient;
        this.credentials = credentials;
        this.objectMapper = objectMapper;
    }

    public String exchangeAuthorizationCodeForToken(String authorizationCode) {
        var request = new Request.Builder()
                .url("https://oauth2.googleapis.com/token")
                .post(new FormBody.Builder()
                        .add("code", authorizationCode)
                        .add("client_id", credentials.clientId)
                        .add("client_secret", credentials.clientSecret)
                        .add("redirect_uri", credentials.redirectUri)
                        .add("grant_type", "authorization_code")
                        .build()
                )
                .build();

        try(var response = httpClient.newCall(request).execute()) {
            return objectMapper.readTree(response.body().string()).get("id_token").asText();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public String extractUserIdFromToken(String token) {
        return JWT.decode(token).getSubject();
    }

    record Credentials(
            String clientId,
            String clientSecret,
            String redirectUri
    ) {}

}
