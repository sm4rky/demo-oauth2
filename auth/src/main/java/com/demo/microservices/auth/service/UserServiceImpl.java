package com.demo.microservices.auth.service;

import com.demo.microservices.auth.entity.User;
import com.demo.microservices.auth.producer.EmailProducer;
import com.demo.microservices.auth.repository.UserRepository;
import com.demo.microservices.common.dto.EmailRequest;
import com.demo.microservices.common.enums.ErrorCode;
import com.demo.microservices.common.exception.ApiException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService{
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;
    EmailProducer emailProducer;

    @Override
    public User save(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new ApiException(ErrorCode.USER_EXISTED);
        }
        emailProducer.sendEmail(new EmailRequest(user.getEmail(), "Verify your email", "Click here"));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new ApiException(ErrorCode.USER_NOT_FOUND));
    }
}
