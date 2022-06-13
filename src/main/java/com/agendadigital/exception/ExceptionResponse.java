package com.agendadigital.exception;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExceptionResponse {
    //private int statusCode;
    //private Date timestamp;
    private String message;
    //private String description;
}
