package com.shekhovtsov.bonus.exception;


import com.shekhovtsov.bonus.dto.ErrorDto;
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



//    //вынести в глобальный перехватчик
//    @ExceptionHandler({ClientNotFoundException.class, Exception.class})
//    private ResponseEntity<String> handleNotFound(Exception e) {
//        log.error(e.getMessage());
//        return new ResponseEntity<ErrorDto>("a", HttpStatus.INTERNAL_SERVER_ERROR);
//        //создаем отдельный класс errorDto(время сам заполняет, сообщение, код ошибки)//все не найденное в ресурснотфауннд второй сервис интернал сервер еерор
//    }
//