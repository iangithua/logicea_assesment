package com.logicea.cards.service;

import com.logicea.cards.auditor.SpringSecurityAuditorAware;
import com.logicea.cards.entity.Card;
import com.logicea.cards.jwt.JwtAuthorizationFilter;
import com.logicea.cards.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CurrencyEditor;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CardService {

    @Autowired
    private CardRepository cardRepository;

    private final AuditorAware<String> currentEditor;

    @Autowired
    public CardService(AuditorAware<String> currentEditor) {
        this.currentEditor = currentEditor;
    }
    public Card createCard(Card card) {
        // Additional validation and business logic can be added here

        return cardRepository.save(card);
    }

    public Card getCardById(Long cardId) {
        System.out.println("currentEditor "+currentEditor.getCurrentAuditor().get());
        Card optionalCard = cardRepository.findByIdAndCreatedBy(cardId,currentEditor.getCurrentAuditor().get());
        return optionalCard;
    }

    public List<Card> getAllCards() {
        return cardRepository.findAll();
    }

    public List<Card> getAllMyCards()  {
        return cardRepository.findAllMyCards(currentEditor.getCurrentAuditor().get());
    }
    public List<Card> getAllMyCardsWithFilters(String name, String color, String status, Date startDate, Date endDate,String sortField)  {
        return cardRepository.searchWithFilters(name,currentEditor.getCurrentAuditor().get(),color,status,startDate,endDate,sortField);
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

