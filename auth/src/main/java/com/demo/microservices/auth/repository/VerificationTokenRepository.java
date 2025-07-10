package com.demo.microservices.auth.repository;

import com.demo.microservices.auth.entity.VerificationToken;
import com.demo.microservices.auth.enums.TokenType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, String> {
    Optional<VerificationToken> findByTokenAndType(String token, TokenType type);
    void deleteByToken(String token);
}
