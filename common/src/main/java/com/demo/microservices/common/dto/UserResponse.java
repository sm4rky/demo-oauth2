package com.demo.microservices.common.dto;

public record UserResponse(
        String id,
        String firstName,
        String lastName,
        String email
) {
}
