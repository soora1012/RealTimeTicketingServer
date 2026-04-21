package com.ticketing.global.error;

import lombok.Getter;

@Getter
public class ErrorResult {
    private final int status;
    private final String message;

    public ErrorResult(ErrorCode errorCode) {
        this.status = errorCode.getStatus().value();
        this.message = errorCode.getMessage();
    }
}