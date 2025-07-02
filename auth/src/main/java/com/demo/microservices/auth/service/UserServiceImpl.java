package com.demo.microservices.auth.service;

import com.demo.microservices.auth.entity.User;
import com.demo.microservices.auth.mapper.UserMapper;
import com.demo.microservices.auth.producer.EmailProducer;
import com.demo.microservices.auth.repository.UserRepository;
import com.demo.microservices.common.dto.EmailRequest;
import com.demo.microservices.common.dto.UserRequest;
import com.demo.microservices.common.dto.UserResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService{
    UserRepository userRepository;
    UserMapper userMapper;
    EmailProducer emailProducer;

    @Override
    public UserResponse save(UserRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new RuntimeException("Email already in use");
        }
        User user = userMapper.toUser(request);

        emailProducer.sendEmail(new EmailRequest(request.email(), "Verify your email", "Click here"));

        return userMapper.toUserResponse(userRepository.save(user));
    }

    @Override
    public List<UserResponse> findAll() {
        return userRepository.findAll().stream()
                .map(userMapper::toUserResponse)
                .toList();
    }
}
