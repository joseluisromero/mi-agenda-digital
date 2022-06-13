package com.agendadigital.exception.handler;


import com.agendadigital.exception.ExceptionResponse;
import com.agendadigital.exception.ValidationServiceCustomer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ControllerExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handlerInvalidArgument(MethodArgumentNotValidException ex) {
        Map<String, String> errorMap = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(fieldError -> {
            errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
        });
        return errorMap;
    }

    @ExceptionHandler(ValidationServiceCustomer.class)
    public ResponseEntity<ExceptionResponse> customExceptionHandler(ValidationServiceCustomer exception) {
        ResponseEntity<ExceptionResponse> response = new ResponseEntity<>(exception.getExceptionResponse(), exception.getHttpStatus());
        return response;
    }

    /*@ExceptionHandler(ConfigDataResourceNotFoundException.class)
    public ResponseEntity<ExceptionResponse> resourceNotFoundException(ConfigDataResourceNotFoundException ex, WebRequest request) {
        ExceptionResponse message = new ExceptionResponse(
                HttpStatus.NOT_FOUND.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(true));

        return new ResponseEntity<ExceptionResponse>(message, HttpStatus.NOT_FOUND);
    }*/

}
