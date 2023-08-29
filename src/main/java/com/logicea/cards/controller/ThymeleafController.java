package com.logicea.cards.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.util.RequestPayload;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.logicea.cards.jwt.JwtConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;

@Controller
public class ThymeleafController {

    private final RestTemplate restTemplate;

    private final JwtConfig jwtConfig;

    @Autowired
    public ThymeleafController(RestTemplate restTemplate, JwtConfig jwtConfig) {
        this.restTemplate = restTemplate;
        this.jwtConfig = jwtConfig;
    }

    @PostMapping("/submit")
    public String handleLogin(Model  userForm, String email, String password) throws JsonProcessingException {

        try {
            // Create JSON payload
            String jsonPayload = "{\"email\":\"" + email + "\",\"password\":\"" + password + "\"}";

            // Set headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // Create HttpEntity with headers and payload
            HttpEntity<String> requestEntity = new HttpEntity<>(jsonPayload, headers);

            // Send POST request using RestTemplate
            String jsonResponse = restTemplate.postForObject(jwtConfig.getServerUrl() + jwtConfig.getUri(), requestEntity, String.class);

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(jsonResponse);

            String token = jsonNode.get("token").asText();

            if (token.isEmpty() || token == null) {
                return "redirect:/ui";
            }
            return "redirect:/card"; // Redirect to dashboard on successful login
        }catch (Exception e)
        {
            return "redirect:/ui";
        }


    }
    @GetMapping("/ui")
    public String showLoginPage() {
        return "login"; // Return the name of the Thymeleaf template without the extension
    }

    @GetMapping("/card")
    public String showCardsPage() {
        return "card"; // Return the name of the Thymeleaf template without the extension
    }
}

