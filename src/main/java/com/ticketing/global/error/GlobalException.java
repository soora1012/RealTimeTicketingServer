package com.ticketing.global.error;

import lombok.Getter;

@Getter
public class GlobalException extends RuntimeException{
    private final int status;
    private final String message;

    public GlobalException(ErrorCode errorCode) {
        this.status = errorCode.getStatus().value();
        this.message = errorCode.getMessage();
    }
}