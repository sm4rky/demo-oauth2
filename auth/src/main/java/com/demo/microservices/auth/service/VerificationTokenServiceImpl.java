package com.demo.microservices.auth.service;

import com.demo.microservices.auth.entity.VerificationToken;
import com.demo.microservices.auth.enums.TokenType;
import com.demo.microservices.auth.repository.VerificationTokenRepository;
import com.demo.microservices.common.enums.ErrorCode;
import com.demo.microservices.common.exception.ApiException;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class VerificationTokenServiceImpl implements VerificationTokenService{
    VerificationTokenRepository verificationTokenRepository;

    @Override
    public void save(VerificationToken verificationToken) {
        verificationTokenRepository.save(verificationToken);
    }

    @Override
    public VerificationToken findByTokenAndType(String token, TokenType tokenType) {
        return verificationTokenRepository.findByTokenAndType(token, tokenType)
                .orElseThrow(() -> new ApiException(ErrorCode.TOKEN_NOT_FOUND));
    }

    @Override
    @Transactional
    public void deleteByToken(String token) {
        verificationTokenRepository.deleteByToken(token);
    }
}
