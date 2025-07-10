package com.demo.microservices.auth.service;

import com.demo.microservices.auth.entity.VerificationToken;
import com.demo.microservices.auth.enums.TokenType;

public interface VerificationTokenService {
    void save(VerificationToken verificationToken);

    VerificationToken findByTokenAndType(String token, TokenType tokenType);

    void deleteByToken(String token);
}
