package com.ticketing.global.error;

import lombok.Getter;
import lombok.Setter;
import org.apache.coyote.Response;


@Getter
public class ApiException extends RuntimeException {
    private final ErrorCode errorCode;

    public ApiException(ErrorCode errorCode) {
        super(errorCode.toString());
        this.errorCode = errorCode;
    }


}
