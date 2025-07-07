package com.demo.microservices.auth.service;


import com.demo.microservices.auth.entity.UserPrincipal;

public interface JwtService {
    String generateAccessToken(UserPrincipal principal);

    String generateRefreshToken(UserPrincipal principal);

    long getExpirySeconds();
}
