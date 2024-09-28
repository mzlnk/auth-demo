package io.mzlnk.authdemo.infrastructure.security;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.oauth2.server.resource.web.BearerTokenResolver;
import org.springframework.util.StringUtils;

public class CookieBearerTokenResolver implements BearerTokenResolver {

    private static final String TOKEN_COOKIE_NAME = "auth_token";

    @Override
    public String resolve(HttpServletRequest request) {
        // Get cookies from the request
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            // Loop through the cookies and find the token cookie
            for (Cookie cookie : cookies) {
                if (TOKEN_COOKIE_NAME.equals(cookie.getName()) && StringUtils.hasText(cookie.getValue())) {
                    return cookie.getValue(); // Return the token from the cookie
                }
            }
        }
        return null; // No valid token found in the cookies
    }
}
