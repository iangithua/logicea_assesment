package com.logicea.cards.repository;

import com.logicea.cards.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Add custom query methods here if needed
    Optional<User> findByEmail(String email);
}

