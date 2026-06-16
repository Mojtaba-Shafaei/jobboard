package com.mojtaba.jobboard.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(JobNotFoundException.class)
    public ErrorResponse handleJobNotFound(JobNotFoundException e, HttpServletRequest request) {
        return new ErrorResponse(
                404,
                "Not Found",
                e.getMessage(),
                request.getRequestURI()
        );
    }
}
