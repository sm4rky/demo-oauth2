package com.demo.microservices.common.exception;

import com.demo.microservices.common.entity.ApiResponse;
import com.demo.microservices.common.enums.ErrorCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = ApiException.class)
    ResponseEntity<ApiResponse<?>> handlingApiException(ApiException apiException) {
        ErrorCode errorCode = apiException.getErrorCode();
        return ResponseEntity
                .status(errorCode.getHttpStatusCode())
                .body(ApiResponse.builder()
                        .message(errorCode.getMessage())
                        .build());
    }

}
