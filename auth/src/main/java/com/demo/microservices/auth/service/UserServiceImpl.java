package com.demo.microservices.auth.service;

import com.demo.microservices.auth.entity.User;
import com.demo.microservices.auth.entity.VerificationToken;
import com.demo.microservices.auth.enums.TokenType;
import com.demo.microservices.auth.producer.EmailProducer;
import com.demo.microservices.auth.repository.UserRepository;
import com.demo.microservices.common.dto.EmailRequest;
import com.demo.microservices.common.enums.ErrorCode;
import com.demo.microservices.common.exception.ApiException;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService{
    UserRepository userRepository;
    VerificationTokenService verificationTokenService;
    PasswordEncoder passwordEncoder;
    EmailProducer emailProducer;

    @NonFinal
    @Value("${app.host}")
    String HOST_URL;

    @Override
    public User save(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new ApiException(ErrorCode.USER_EXISTED);
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);

        String token = UUID.randomUUID().toString();
        verificationTokenService.save(VerificationToken.builder()
                .token(token)
                .type(TokenType.VERIFY_EMAIL)
                .user(savedUser)
                .expiresAt(Instant.now().plus(24, ChronoUnit.HOURS))
                .build());

        String link = String.format("%s/api/v1/user/verify?token=%s", HOST_URL, token);
        String body = "Hi " + savedUser.getFirstName() + ",\n\nClick the link below to verify your account:\n" + link;
        emailProducer.sendEmail(new EmailRequest(
                savedUser.getEmail(),
                "Verify your e-mail",
                body));
        return savedUser;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ApiException(ErrorCode.USER_NOT_FOUND));
    }

    @Override
    public User findById(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ApiException(ErrorCode.USER_NOT_FOUND));
    }

    @Override
    @Transactional
    public void verifyUser(String token) {
        VerificationToken verificationToken = verificationTokenService.findByTokenAndType(token, TokenType.VERIFY_EMAIL);
        if (verificationToken.getExpiresAt().isBefore(Instant.now())) throw new ApiException(ErrorCode.TOKEN_EXPIRED);
        User user = verificationToken.getUser();
        user.setIsVerified(true);
        userRepository.save(user);
        verificationTokenService.deleteByToken(token);
    }

    @Override
    public void generateResetPasswordToken(String email) {
        User user = findByEmail(email);
        String token = UUID.randomUUID().toString();
        verificationTokenService.save(VerificationToken.builder()
                .token(token)
                .type(TokenType.RESET_PASSWORD)
                .user(user)
                .expiresAt(Instant.now().plus(15, ChronoUnit.MINUTES))
                .build());

        String link = String.format("%s/password/reset?token=%s", HOST_URL, token);
        String body = "Hi " + user.getFirstName() + ",\n\nClick the link below to reset your password:\n" + link;
        emailProducer.sendEmail(new EmailRequest(
                email,
                "Reset your password",
                body));
    }

    @Override
    public void verifyResetPasswordToken(String token) {
        VerificationToken verificationToken = verificationTokenService.findByTokenAndType(token, TokenType.RESET_PASSWORD);
        if (verificationToken.getExpiresAt().isBefore(Instant.now())) throw new ApiException(ErrorCode.TOKEN_EXPIRED);
    }

    @Override
    @Transactional
    public void resetPassword(String token, String newPassword) {
        VerificationToken verificationToken = verificationTokenService.findByTokenAndType(token, TokenType.RESET_PASSWORD);
        if (verificationToken.getExpiresAt().isBefore(Instant.now())) throw new ApiException(ErrorCode.TOKEN_EXPIRED);
        User user = verificationToken.getUser();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        verificationTokenService.deleteByToken(token);
    }

    @Override
    @Transactional
    @Scheduled(cron = "${app.scheduler.delete-unverified-users-cron}")
    public void deleteUnverifiedUsers() {
        userRepository.deleteByIsVerifiedFalseAndCreatedAtBefore(Instant.now().minus(2, ChronoUnit.DAYS));
    }
}
