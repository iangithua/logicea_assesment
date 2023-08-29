package com.logicea.cards.jwt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "jwt")
@Configuration("jwtConfig")
public class JwtConfig {

    private String secret;
    private int expiration;
    private String uri;
    private String card_uri;
    private String serverUrl;
    private String headString;
    private String prefix;

    // Load from application.properties or application.yml
    public JwtConfig(@Value("${jwt.secret}") String secret,@Value("${jwt.expiration}") int expiration,@Value("${jwt.uri}") String uri,@Value("${jwt.card-uri}") String card_uri,
                     @Value("${jtw.server-url}") String serverUrl, @Value("${jwt.headstring}") String headString,@Value("${jwt.prefix}") String prefix) {
        this.secret = secret;
        this.expiration = expiration;
        this.uri = uri;
        this.card_uri=card_uri;
        this.serverUrl=serverUrl;
        this.headString = headString;
        this.prefix = prefix;
        System.out.println("JwtConfig Constructer "+" secret "+this.secret+" headString "+headString);
    }

    public String getSecret() {
        return secret;
    }

    public int getExpiration() {
        return expiration;
    }

    public String getUri() {
        return uri;
    }

    public String getServerUrl() {
        return serverUrl;
    }

    public String getCard_uri() {
        return card_uri;
    }

    public String getHeadString() {return headString;}
    public String getPrefix() {return prefix;}
}
