package com.shekhovtsov.bonus.exception;


import com.shekhovtsov.bonus.dto.ErrorDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDto>  catchResourceNotFoundException(ResourceNotFoundException exception) {
        log.error(exception.getMessage(), exception);
        return new ResponseEntity<>(new ErrorDto(exception.getMessage(), "RESOURCE_NOT_FOUND"), HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto>  catchCriticalException(Exception exception) {
        log.error(exception.getMessage(), exception);
        return new ResponseEntity<>(new ErrorDto(exception.getMessage(), "CRITICAL_ERROR"), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //новый метод в котором новый эксепшн и 400

}