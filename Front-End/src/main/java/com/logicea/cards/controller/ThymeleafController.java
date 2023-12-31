package com.logicea.cards.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.logicea.cards.config.Configs;
import com.logicea.cards.entity.Card;
import com.logicea.cards.entity.Payload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ThymeleafController {

    private final RestTemplate restTemplate;

    private final Configs jwtConfig;
    private final Map<String, String> inMemoryStorage;

    @Autowired
    public ThymeleafController(RestTemplate restTemplate, Configs jwtConfig, Map<String, String> inMemoryStorage) {
        this.restTemplate = restTemplate;
        this.jwtConfig = jwtConfig;
        this.inMemoryStorage = inMemoryStorage;
    }

    @GetMapping("/admin")
    public ResponseEntity<Map<String, Object>> getAdminPage() throws JsonProcessingException{

        String token=inMemoryStorage.get("token");
        // Set headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Bearer "+token);

        // Create HttpEntity with headers and payload
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        // Make a REST template call to another service to fetch paginated data
//        ResponseEntity<Map> response = restTemplate.getForEntity("http://localhost:8080/api/cards/all-cards", Map.class,requestEntity);
        ResponseEntity<List> jsonResponse = restTemplate.exchange("http://localhost:8080/api/cards/all-cards", HttpMethod.GET, requestEntity, List.class);

        System.out.println("jsonResponse"+jsonResponse.getBody());
        System.out.println("jsonResponse" + jsonResponse.getBody());

        List<Map<String, Object>> content = jsonResponse.getBody();

// Now you can create a new responseData map
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("content", content);


        return ResponseEntity.ok(responseData);
    }
    @GetMapping("/paginated")
    public ResponseEntity<Map<String, Object>> getPaginatedCards(@RequestParam int page,@RequestParam int size,@RequestParam String name,
            @RequestParam String color,@RequestParam String status
            ,@RequestParam String date,@RequestParam String sortField
            ) throws JsonProcessingException{


        System.out.println("paginated mapping "+name+color+status+date+sortField);
        String token=inMemoryStorage.get("token");

        ObjectMapper objectMapper = new ObjectMapper();
        Payload payload = new Payload();
        payload.setPage(page);
        payload.setSize(size);
        payload.setName(name);
        payload.setColor(color);
        payload.setStatus(status);
        payload.setDate(date);
        payload.setSortField(sortField);

        String jsonPayload = objectMapper.writeValueAsString(payload);

        System.out.println("jsonPayload "+jsonPayload);
        // Set headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Bearer "+token);

        // Create HttpEntity with headers and payload
        HttpEntity<String> requestEntity = new HttpEntity<>(jsonPayload, headers);
        // Make a REST template call to another service to fetch paginated data
        ResponseEntity<Map> response = restTemplate.postForEntity("http://localhost:8080/api/cards/search/",requestEntity, Map.class);

        Map<String, Object> responseData = new HashMap<>();
        responseData.put("content", response.getBody().get("content"));
        responseData.put("pagination", response.getBody());


        return ResponseEntity.ok(responseData);
    }

    @PostMapping("/submit")
    public String handleLogin(Model userForm, String email, String password) throws JsonProcessingException {

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
            inMemoryStorage.put("token",token);

            if (token.isEmpty() || token == null) {
                return "redirect:/login";
            }
            return "redirect:/card"; // Redirect to dashboard on successful login
        }catch (Exception e)
        {
            return "redirect:/login";
        }
    }
    @GetMapping("/login")
    public String showLoginPage() {
        return "login"; // Return the name of the Thymeleaf template without the extension
    }

    @GetMapping("/get-card/{cardId}")
    public ResponseEntity<Card> getCard(@PathVariable String cardId) {
        Long stringCardId = Long.parseLong(cardId);
        System.out.println("getCard");
        String cardsObject = inMemoryStorage.get("cards");
        ObjectMapper objectMapper = new ObjectMapper();
        Card responseCard;
        try {
            Card[] cards = objectMapper.readValue(cardsObject, Card[].class);

            // Now you have an array of Card objects
            for (Card card : cards) {
                if(card.getId()== stringCardId.longValue())
                {
                    responseCard=card;
                    System.out.println("found card"+responseCard.getName());
                    return ResponseEntity.ok(responseCard);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.notFound().build();
    }
    @PostMapping("/card-delete")
    public String deleteCard(Long id) {
        String token=inMemoryStorage.get("token");
        System.out.println("delete "+id);

        try{
            // Set headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add("Authorization", "Bearer "+token);
            System.out.println("URL "+jwtConfig.getServerUrl() + jwtConfig.getCard_uri());
            // Create HttpEntity with headers and payload
            HttpEntity<String> requestEntity = new HttpEntity<>(headers);
            // Send POST request using RestTemplate

            ResponseEntity<String> jsonResponse = restTemplate.exchange(
                    jwtConfig.getServerUrl() + jwtConfig.getCard_uri()+"/"+id, HttpMethod.DELETE, requestEntity, String.class);

            System.out.println("jsonResponse "+jsonResponse.getBody());

        } catch (Exception e) {

        }
        return "redirect:card";
    }
    @PostMapping("/card-update")
    public String updateCard(Long id, String name, String description, String color, String status) {
        String token=inMemoryStorage.get("token");
        System.out.println("token passed "+token);

        try{
        // Create JSON payload
        String jsonPayload = "{\"name\":\"" + name + "\",\"description\":\"" + description + "\",\"color\":\"" + color + "\",\"status\":\"" + status + "\"}";
        // Set headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Bearer "+token);
        System.out.println("URL "+jwtConfig.getServerUrl() + jwtConfig.getCard_uri());
        // Create HttpEntity with headers and payload
        HttpEntity<String> requestEntity = new HttpEntity<>(jsonPayload, headers);
        // Send POST request using RestTemplate

        ResponseEntity<String> jsonResponse = restTemplate.exchange(
                jwtConfig.getServerUrl() + jwtConfig.getCard_uri()+"/"+id, HttpMethod.PUT, requestEntity, String.class);

        System.out.println("jsonResponse "+jsonResponse.getBody());

    } catch (Exception e) {

    }
        return "redirect:card";
    }
    @GetMapping("/card")
    public String showCardsPage(Model model) {
        ////TODO PASS/STORE TOKEN SAFELY
        String token=inMemoryStorage.get("token");
        System.out.println("token passed "+token);
        // Set headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Bearer "+token);
        System.out.println("URL "+jwtConfig.getServerUrl() + jwtConfig.getCard_uri());
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        // Send POST request using RestTemplate
//        String jsonResponse = restTemplate.getForObject(jwtConfig.getServerUrl() + jwtConfig.getCard_uri()+"/my-cards", String.class);
        ResponseEntity<String> jsonResponse = restTemplate.exchange(
                jwtConfig.getServerUrl() + jwtConfig.getCard_uri()+"/my-cards", HttpMethod.GET, requestEntity, String.class);

        inMemoryStorage.put("cards",jsonResponse.getBody());
        System.out.println("jsonResponse "+jsonResponse.getBody());
        ObjectMapper objectMapper = new ObjectMapper();
        List<Card> cardsList =new ArrayList<Card>();
        try {
            Card[] cards = objectMapper.readValue(jsonResponse.getBody(), Card[].class);

            // Now you have an array of Card objects
            for (Card card : cards) {
                cardsList.add(card);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("cards", cardsList);

        return "card"; // Return the name of the Thymeleaf template without the extension
    }

    @PostMapping("/card-new")
    public String handleNewCard(Model  userCard, String name, String description, String color, String status) throws JsonProcessingException {
        System.out.println("name " + name + " desc " + description + color + status);
        String token=inMemoryStorage.get("token");
        try {
            // Create JSON payload
            String jsonPayload = "{\"name\":\"" + name + "\",\"description\":\"" + description + "\",\"color\":\"" + color + "\",\"status\":\"" + status + "\"}";

            // Set headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add("Authorization", "Bearer "+token);

            // Create HttpEntity with headers and payload
            HttpEntity<String> requestEntity = new HttpEntity<>(jsonPayload, headers);

            // Send POST request using RestTemplate
            String jsonResponse = restTemplate.postForObject(jwtConfig.getServerUrl() + jwtConfig.getCard_uri(), requestEntity, String.class);

            System.out.println("jsonResponse new card "+jsonResponse);


        } catch (Exception e) {

        }
        return "redirect:card";
    }
}

