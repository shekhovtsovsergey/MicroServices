package com.example.core.exception;

import com.example.core.dto.AppError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<AppError> catchResourceNotFoundException(ResourceNotFoundException e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(new AppError(HttpStatus.NOT_FOUND.value(), e.getMessage()), HttpStatus.NOT_FOUND);//брать код из ошибки
    }

    @ExceptionHandler
    public ResponseEntity<ValidationError> catchResourceNotFoundException(ValidationException e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(new ValidationError(e.getFields(), e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    public static class ResourceNotFoundException extends RuntimeException {
        public ResourceNotFoundException(String message) {
            super(message);
        }
    }
}
