package com.logicea.cards.repository;

import com.logicea.cards.entity.Card;
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
            "WHERE (:name IS NULL OR c.name LIKE %:name%) " +
            "AND (:userName IS NULL OR c.created_by =:userName) " +
            "AND (:color IS NULL OR c.color LIKE %:color%) " +
            "AND (:status IS NULL OR c.status LIKE %:status%) " +
            "AND (:startDate IS NULL OR c.created_at >= :startDate) " +
            "AND (:endDate IS NULL OR c.created_at <= :endDate)"+
            "ORDER BY " +
            "CASE WHEN :sortField = 'name' THEN c.name END ASC, " +
            "CASE WHEN :sortField = 'color' THEN c.color END ASC, " +
            "CASE WHEN :sortField = 'status' THEN c.status END ASC, " +
            "CASE WHEN :sortField = 'createdAt' THEN c.created_at END ASC", nativeQuery = true)
    List<Card> searchWithFilters(
            String name,String userName, String color, String status, Date startDate, Date endDate,String sortField);
}
