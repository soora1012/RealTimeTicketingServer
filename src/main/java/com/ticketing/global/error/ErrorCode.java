package com.ticketing.global.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    INVALID_REQUEST(HttpStatus.BAD_REQUEST, "INVALID_REQUEST"),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "UNAUTHORIZED"),
    FORBIDDEN(HttpStatus.FORBIDDEN, "FORBIDDEN"),
    USER_EXISTS(HttpStatus.CONFLICT, "USER_EXISTS"),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER_NOT_FOUND"),
    INVALID_CREDENTIALS(HttpStatus.UNAUTHORIZED, "INVALID_CREDENTIALS"),
    TICKET_NOT_FOUND(HttpStatus.NOT_FOUND, "TICKET_NOT_FOUND"),
    INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_ERROR");

    private final HttpStatus status;
    private final String message;

    ErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
