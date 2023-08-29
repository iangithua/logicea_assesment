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

    private String serverUrl;
    private String headString;
    private String prefix;

    // Load from application.properties or application.yml
    public JwtConfig(@Value("${jwt.secret}") String secret,@Value("${jwt.expiration}") int expiration,@Value("${jwt.uri}") String uri,
                     @Value("${jtw.server-url}") String serverUrl, @Value("${jwt.headstring}") String headString,@Value("${jwt.prefix}") String prefix) {
        this.secret = secret;
        this.expiration = expiration;
        this.uri = uri;
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

    public String getHeadString() {return headString;}
    public String getPrefix() {return prefix;}
}
