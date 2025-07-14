package com.demo.microservices.auth.service;

import com.demo.microservices.auth.entity.User;

import java.util.List;

public interface UserService {
    User save(User user);

    List<User> findAll();

    User findByEmail(String email);

    User findById(String id);

    void verifyUser(String token);

    void generateResetPasswordToken(String email);

    void verifyResetPasswordToken(String token);

    void resetPassword(String token, String newPassword);

    void deleteUnverifiedUsers();
}
