package com.mojtaba.jobboard.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import java.util.Collections;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class GlobalExceptionHandler {

  // 400 - Validation errors
  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorResponse handleValidationErrors(
      MethodArgumentNotValidException ex, HttpServletRequest request) {
    List<String> details =
        ex.getBindingResult().getFieldErrors().stream()
            .map(err -> err.getField() + ": " + err.getDefaultMessage())
            .toList();

    return new ErrorResponse(
        400,
        "Validation Failed",
        "Input validation error",
        request.getRequestURI(),
        request.getMethod(),
        details);
  }

  // 400 - Constraint violations (e.g. @Validated on path/query params)
  @ExceptionHandler(ConstraintViolationException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorResponse handleConstraintViolation(
      ConstraintViolationException ex, HttpServletRequest request) {
    List<String> details =
        ex.getConstraintViolations().stream()
            .map(v -> v.getPropertyPath() + ": " + v.getMessage())
            .toList();

    return new ErrorResponse(
        400,
        "Validation Failed",
        "Constraint violation",
        request.getRequestURI(),
        request.getMethod(),
        details);
  }

  // 400 - Wrong parameter type (e.g. string passed as Long)
  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorResponse handleTypeMismatch(
      MethodArgumentTypeMismatchException ex, HttpServletRequest request) {
    String message =
        String.format(
            "Parameter '%s' should be of type '%s'",
            ex.getName(),
            ex.getRequiredType() != null ? ex.getRequiredType().getSimpleName() : "unknown");
    return new ErrorResponse(
        400,
        "Bad Request",
        message,
        request.getRequestURI(),
        request.getMethod(),
        Collections.emptyList());
  }

  // 401 - Wrong Credential
  @ExceptionHandler(UnauthorizedException.class)
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  public ResponseEntity<ErrorResponse> handleAuth(
      UnauthorizedException ex, HttpServletRequest request) {
    return ResponseEntity.status(401)
        .body(
            new ErrorResponse(
                401,
                "Unauthorized",
                ex.getMessage(),
                request.getRequestURI(),
                request.getMethod(),
                Collections.emptyList()));
  }

  // 422 - Business rule violations
  @ExceptionHandler(BusinessException.class)
  public ResponseEntity<ErrorResponse> handleBusiness(
      BusinessException ex, HttpServletRequest request) {
    return ResponseEntity.status(422)
        .body(
            new ErrorResponse(
                422,
                "Business Error",
                ex.getMessage(),
                request.getRequestURI(),
                request.getMethod(),
                Collections.emptyList()));
  }

  // 404 - Resource not found
  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler(JobNotFoundException.class)
  public ErrorResponse handleJobNotFound(JobNotFoundException e, HttpServletRequest request) {
    return new ErrorResponse(
        404,
        "Not Found",
        e.getMessage(),
        request.getRequestURI(),
        request.getMethod(),
        Collections.emptyList());
  }
}
