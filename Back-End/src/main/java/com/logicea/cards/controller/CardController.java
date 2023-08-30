package com.logicea.cards.controller;

import com.logicea.cards.entity.Card;
import com.logicea.cards.entity.SearchRequest;
import com.logicea.cards.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/cards")
public class CardController {

    @Autowired
    private CardService cardService;

    @PostMapping("/search")
    public ResponseEntity<Page<Card>> getAllMyCardsWithFilters(@RequestBody SearchRequest searchRequest) {
        System.out.println("search result "+searchRequest.getName()+searchRequest.getColor()+searchRequest.getStatus()+searchRequest.getCreated_at()+searchRequest.getSortField()+ searchRequest.getPage()+ searchRequest.getSize());

        Page<Card> createdCard = cardService.getAllMyCardsWithFilters(searchRequest.getName(),searchRequest.getColor(),searchRequest.getStatus(),searchRequest.getCreated_at(),searchRequest.getSortField(), searchRequest.getPage(), searchRequest.getSize());
        return new ResponseEntity<>(createdCard, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Card> createCard(@RequestBody Card card) {
        Card createdCard = cardService.createCard(card);
        return new ResponseEntity<>(createdCard, HttpStatus.CREATED);
    }

    @GetMapping("/{cardId}")
    public ResponseEntity<Card> getCardById(@PathVariable Long cardId) {
        Card card = cardService.getCardById(cardId);
        return new ResponseEntity<>(card, HttpStatus.OK);
    }

    //Admin Only
    @GetMapping("/all-cards")
    public ResponseEntity<List<Card>> getAllCards() {
        List<Card> cards = cardService.getAllCards();
        return new ResponseEntity<>(cards, HttpStatus.OK);
    }

    @GetMapping("/my-cards")
    public ResponseEntity<List<Card>> getAllMyCards() {
        List<Card> cards = cardService.getAllMyCards();
        return new ResponseEntity<>(cards, HttpStatus.OK);
    }

    @PutMapping("/{cardId}")
    public ResponseEntity<Card> updateCard(@PathVariable Long cardId, @RequestBody Card card) {
        Card updatedCard = cardService.updateCard(cardId, card);
        return new ResponseEntity<>(updatedCard, HttpStatus.OK);
    }

    @DeleteMapping("/{cardId}")
    public ResponseEntity<Void> deleteCard(@PathVariable Long cardId) {
        cardService.deleteCard(cardId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
