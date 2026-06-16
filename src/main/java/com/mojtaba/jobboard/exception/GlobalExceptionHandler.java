package com.mojtaba.jobboard.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(JobNotFoundException.class)
    public Map<String, String> handleJobNotFound(JobNotFoundException e) {
        return Map.of("error", e.getMessage(),
                "timestamp", String.valueOf(System.currentTimeMillis()));
    }
}
