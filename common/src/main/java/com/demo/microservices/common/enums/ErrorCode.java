package com.demo.microservices.common.enums;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    USER_EXISTED(HttpStatus.BAD_REQUEST, "User already existed"),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "User not found"),
    USER_BAD_CREDENTIALS(HttpStatus.UNAUTHORIZED, "Email or password is incorrect"),
    ;

    private HttpStatusCode httpStatusCode;
    private String message;

    ErrorCode(HttpStatusCode httpStatusCode, String message) {
        this.httpStatusCode = httpStatusCode;
        this.message = message;
    }
}
