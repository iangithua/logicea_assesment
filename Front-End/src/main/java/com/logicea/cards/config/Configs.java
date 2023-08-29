package com.logicea.cards.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@ConfigurationProperties(prefix = "jwt")
@Configuration("jwtConfig")
public class Configs {
    private String serverUrl;
    private String uri;
    private String card_uri;

    @Bean
    public Map<String, String> inMemoryStorage() {
        return new HashMap<>();
    }
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {

        return builder
                .setConnectTimeout(Duration.ofMillis(3000))
                .setReadTimeout(Duration.ofMillis(3000))
                .build();
    }
    public Configs(@Value("${jwt.uri}") String uri, @Value("${jwt.card-uri}") String card_uri,
                     @Value("${jtw.server-url}") String serverUrl) {
        this.uri = uri;
        this.card_uri=card_uri;
        this.serverUrl=serverUrl;
    }

    public String getServerUrl() {
        return serverUrl;
    }

    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getCard_uri() {
        return card_uri;
    }

    public void setCard_uri(String card_uri) {
        this.card_uri = card_uri;
    }
}
