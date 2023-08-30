package com.logicea.cards.repository;

import com.logicea.cards.entity.Card;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
    @Query(value = "SELECT * FROM Card u WHERE u.id = :cardId AND u.created_by = :currentAuditor", nativeQuery = true)
    Card findByIdAndCreatedBy( Long cardId, String currentAuditor);

    @Query(value = "SELECT * FROM Card u WHERE u.created_by = :currentAuditor", nativeQuery = true)
    List<Card> findAllMyCards(String currentAuditor);

    @Query(value = "SELECT * FROM Card c " +
            "WHERE (:userName IS NOT NULL AND c.created_by =:userName) " +
            "AND (:name IS NULL OR c.name LIKE %:name%) " +
            "AND (:color IS NULL OR c.color LIKE %:color%) " +
            "AND (:status IS NULL OR c.status LIKE %:status%) " +
            "AND (:date IS NULL OR c.created_at LIKE %:date%) ", nativeQuery = true)
    Page<Card> searchWithFilters(String name,String userName, String color, String status, Date date, Pageable pageable);
}
