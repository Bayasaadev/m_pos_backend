package com.bank.pos.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiError> handleBadRequest(BadRequestException ex) {
        if (ex.getResponseCode() != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiError(ex.getMessage(), ex.getResponseCode()));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiError(ex.getMessage()));
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiError> handleNotFound(NotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiError(ex.getMessage()));
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ApiError> handleConflict(ConflictException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiError(ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidation(MethodArgumentNotValidException ex) {
        List<ApiError.FieldError> details = ex.getBindingResult().getFieldErrors().stream()
                .map(this::toFieldError)
                .toList();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiError("Validation failed", details));
    }

    private ApiError.FieldError toFieldError(FieldError fe) {
        return new ApiError.FieldError(fe.getField(), fe.getDefaultMessage());
    }
}


