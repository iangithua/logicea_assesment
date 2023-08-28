package com.logicea.cards.service;

import com.logicea.cards.entity.Card;
import com.logicea.cards.jwt.JwtAuthorizationFilter;
import com.logicea.cards.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CardService {

    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private JwtAuthorizationFilter jwtAuthorizationFilter;

    public Card createCard(Card card) {
        // Additional validation and business logic can be added here

        return cardRepository.save(card);
    }

    public Card getCardById(Long cardId) {
        Optional<Card> optionalCard = cardRepository.findById(cardId);
        return optionalCard.orElse(null);
    }

    public List<Card> getAllCards() {
        return cardRepository.findAll();
    }

    public Card updateCard(Long cardId, Card card) {
        Optional<Card> optionalCard = cardRepository.findById(cardId);
        if (optionalCard.isPresent()) {
            Card existingCard = optionalCard.get();
            existingCard.setName(card.getName());
            existingCard.setDescription(card.getDescription());
            existingCard.setColor(card.getColor());
            existingCard.setStatus(card.getStatus());
            // Additional fields can be updated here
            return cardRepository.save(existingCard);
        }
        return null;
    }

    public void deleteCard(Long cardId) {
        cardRepository.deleteById(cardId);
    }
}

