package com.logicea.cards.jwt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {

    @Value("${jwt.secret}") // Load from application.properties or application.yml
    private String secret;

    @Value("${jwt.expiration}") // Load from application.properties or application.yml
    private int expiration;

    @Value("${jwt.uri}") // Load from application.properties or application.yml
    private String uri;

//    @Bean
//    public JwtUtils jwtUtils() {
//        return new JwtUtils(secret, expiration);
//    }

    public String getSecret() {
        return secret;
    }

    public int getExpiration() {
        return expiration;
    }

    public String getUri() {
        return uri;
    }
}
