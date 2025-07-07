package com.demo.microservices.auth.exception;

import com.demo.microservices.common.entity.ApiResponse;
import com.demo.microservices.common.enums.ErrorCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class SecurityExceptionHandler {
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiResponse<?>> handlingAuthenticationException(AuthenticationException authenticationException) {
        ErrorCode errorCode = ErrorCode.USER_BAD_CREDENTIALS;
        return ResponseEntity
                .status(errorCode.getHttpStatusCode())
                .body(ApiResponse.builder()
                        .message(errorCode.getMessage())
                        .build());
    }
}
