package com.samjsddevelopment.restapitemplate.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    //TODO: Replace with custom exception
    @ExceptionHandler(CustomException.class)
    public ProblemDetail genericException(CustomException ex) {
        var pd = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        return pd;
    }

}
