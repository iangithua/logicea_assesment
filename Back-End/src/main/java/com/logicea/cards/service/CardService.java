package com.logicea.cards.service;

import com.logicea.cards.auditor.SpringSecurityAuditorAware;
import com.logicea.cards.entity.Card;
import com.logicea.cards.jwt.JwtAuthorizationFilter;
import com.logicea.cards.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CurrencyEditor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class CardService {

    @Autowired
    private CardRepository cardRepository;

    private final AuditorAware<String> currentEditor;

    private static final String COLOR_REGEX = "^#([A-Fa-f0-9]{6})$"; // Regex pattern for color validation
    private static final Pattern COLOR_PATTERN = Pattern.compile(COLOR_REGEX);

    @Autowired
    public CardService(AuditorAware<String> currentEditor) {
        this.currentEditor = currentEditor;
    }
    public Card createCard(Card card) {
        // Additional validation and business logic can be added here
        if (isValidColorFormat(card.getColor())) {
            return cardRepository.save(card);
        } else {
            throw new IllegalArgumentException("Invalid color format");
        }
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
    public Page<Card> getAllMyCardsWithFilters(String name, String color, String status, Date date, String sortField,int page,int size)  {
        Pageable pageable=null;
        //Defaults
        int finalPage = page == 0 ? 1 : page;
        int finalSize =size== 0 ? 5 : size;
        if (sortField != null && !sortField.isEmpty()) {
             pageable = PageRequest.of(finalPage, finalSize, Sort.by(sortField));
        }
        else
        {
            pageable = PageRequest.of(finalPage, finalSize);
        }
        return cardRepository.searchWithFilters(name,currentEditor.getCurrentAuditor().get(),color,status,date,pageable);
    }

    public Card updateCard(Long cardId, Card card) {
        Optional<Card> optionalCard = cardRepository.findById(cardId);
        if(!optionalCard.get().getCreatedBy().equals(currentEditor.getCurrentAuditor().get()))
        {
            throw new IllegalArgumentException("You are not the Owner");
        }
        if (optionalCard.isPresent()) {
            Card existingCard = optionalCard.get();
            existingCard.setName(card.getName());
            existingCard.setDescription(card.getDescription());
            existingCard.setStatus(card.getStatus());
            existingCard.setColor(card.getColor());
            if (isValidColorFormat(card.getColor())) {
                return cardRepository.save(existingCard);
            } else {
                throw new IllegalArgumentException("Invalid color format");
            }

        }
        return null;
    }

    public void deleteCard(Long cardId) {
        Card confirmationCard = getCardById(cardId);

        if(confirmationCard.getCreatedBy()==null)
        {
            throw new IllegalArgumentException("You are not the Owner OR the data is already deleted");
        }
        cardRepository.deleteById(cardId);
    }
    private boolean isValidColorFormat(String color) {
        return color == null || COLOR_PATTERN.matcher(color).matches();
    }

}

