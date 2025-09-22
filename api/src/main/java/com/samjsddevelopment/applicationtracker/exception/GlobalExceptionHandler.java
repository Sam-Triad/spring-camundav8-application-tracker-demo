package com.samjsddevelopment.applicationtracker.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(ApplicationException.class)
    public ProblemDetail genericException(ApplicationException ex) {
        var pd = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        return pd;
    }

}
