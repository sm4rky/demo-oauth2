package com.demo.microservices.common.dto;

public record EmailRequest (
    String to,
    String subject,
    String body
) {
}
