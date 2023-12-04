package com.shekhovtsov.core.exception;

import com.shekhovtsov.core.dto.ErrorDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public ErrorDto catchResourceNotFoundException(ResponseStatusException exception) {
        log.error(exception.getMessage(), exception.getRawStatusCode());
        return new ErrorDto(exception.getMessage(), exception.getRawStatusCode());
    }
}
