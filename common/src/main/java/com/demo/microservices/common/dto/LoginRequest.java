package com.demo.microservices.common.dto;

public record LoginRequest(
    String email,
    String password
) {
}
