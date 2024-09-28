package io.mzlnk.authdemo.infrastructure.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
class SecurityConfiguration {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/api/test/non-secure").permitAll();
                    auth.requestMatchers("/api/auth/redirect/google").permitAll();
                    auth.anyRequest().authenticated();
                })
                .oauth2ResourceServer(oauth2 ->
                        oauth2
                                .jwt(jwt -> {})
                                .bearerTokenResolver(new CookieBearerTokenResolver())
                )
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .csrf(AbstractHttpConfigurer::disable)
                .build();

    }



}
