package com.ticketing.global.error;

import com.ticketing.global.api.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiResponse<Void>> handleApi(ApiException e) {
        return ResponseEntity.status(e.getErrorCode().getStatus())
                .body(ApiResponse.fail(e.getErrorCode().getMessage()));
    }

    @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class, IllegalArgumentException.class})
    public ResponseEntity<ApiResponse<Void>> handleBadRequest(Exception e) {
        return ResponseEntity.badRequest().body(ApiResponse.fail(ErrorCode.INVALID_REQUEST.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleUnknown(Exception e) {
        return ResponseEntity.internalServerError().body(ApiResponse.fail("INTERNAL_ERROR"));
    }
}
