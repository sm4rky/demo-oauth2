package com.demo.microservices.auth.service;

import com.demo.microservices.common.dto.UserRequest;
import com.demo.microservices.common.dto.UserResponse;

import java.util.List;

public interface UserService {
    UserResponse save(UserRequest request);

    List<UserResponse> findAll();
}
