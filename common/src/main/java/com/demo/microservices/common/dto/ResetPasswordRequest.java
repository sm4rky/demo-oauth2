package com.demo.microservices.common.dto;

public record ResetPasswordRequest(
    String token,
    String newPassword
) {
}
