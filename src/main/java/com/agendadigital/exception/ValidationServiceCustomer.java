package com.agendadigital.exception;

import org.springframework.http.HttpStatus;

public class ValidationServiceCustomer extends RuntimeException {
    private HttpStatus httpStatus;

    public ValidationServiceCustomer(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public ExceptionCustomerResponse getExceptionResponse() {
        return ExceptionCustomerResponse.builder()
                .message(getLocalizedMessage())
                .build();
    }
}
