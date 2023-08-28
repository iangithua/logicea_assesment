package com.logicea.cards.repository;

import com.logicea.cards.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
    @Query(value = "SELECT * FROM Card u WHERE u.id = :cardId AND u.created_by = :currentAuditor", nativeQuery = true)
    Card findByIdAndCreatedBy( Long cardId, String currentAuditor);

    @Query(value = "SELECT * FROM Card u WHERE u.created_by = :currentAuditor", nativeQuery = true)
    List<Card> findAllMyCards(String currentAuditor);
}
