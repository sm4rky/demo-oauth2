package com.demo.microservices.common.dto;

public record AuthResponse(
        String accessToken,
        long expiresIn,
        String refreshToken,
        String tokenType,
        UserResponse user
) {
}
