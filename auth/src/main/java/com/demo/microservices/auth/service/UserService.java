package com.demo.microservices.auth.service;

import com.demo.microservices.auth.entity.User;
import com.demo.microservices.common.dto.UserRequest;
import com.demo.microservices.common.dto.UserResponse;

import java.util.List;

public interface UserService {
    User save(User user);

    List<User> findAll();

    User findByEmail(String email);
}
