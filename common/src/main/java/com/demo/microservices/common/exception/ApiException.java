package com.demo.microservices.common.exception;

import com.demo.microservices.common.enums.ErrorCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiException extends RuntimeException {
    private ErrorCode errorCode;

    public ApiException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
