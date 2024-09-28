package io.mzlnk.authdemo.api;

import io.mzlnk.authdemo.auth.AuthFacade;
import io.mzlnk.authdemo.auth.AuthProvider;
import io.mzlnk.authdemo.auth.AuthUser;
import io.mzlnk.authdemo.auth.GoogleOAuth2Exchange;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
class AuthController {

    private final AuthFacade authFacade;
    private final GoogleOAuth2Exchange googleOAuth2Exchange;

    AuthController(AuthFacade authFacade, GoogleOAuth2Exchange googleOAuth2Exchange) {
        this.authFacade = authFacade;
        this.googleOAuth2Exchange = googleOAuth2Exchange;
    }

    @GetMapping
    @RequestMapping("/redirect/google")
    @ResponseStatus(HttpStatus.OK)
    public void handleGoogleRedirect(
            @RequestParam("code") String code,
            HttpServletResponse httpResponse
        ) {
        var token = Optional.of(code)
                .map(googleOAuth2Exchange::exchangeAuthorizationCodeForToken)
                .map(googleOAuth2Exchange::extractUserIdFromToken)
                .map(googleUserId -> authFacade.generateToken(new AuthUser.Id(googleUserId), AuthProvider.GOOGLE))
                .orElseThrow();

        var cookie = new Cookie("auth_token", token.value());
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        httpResponse.addCookie(cookie);
    }

}
