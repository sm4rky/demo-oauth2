package com.demo.microservices.common.dto;

public record UserRequest(
    String firstName,
    String lastName,
    String email,
    String password
) {
}
