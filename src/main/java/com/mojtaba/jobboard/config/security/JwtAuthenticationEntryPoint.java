package com.mojtaba.jobboard.config.security;

import com.mojtaba.jobboard.exception.ErrorResponse;

import org.jspecify.annotations.NonNull;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collections;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tools.jackson.databind.ObjectMapper;

/** This class is used to handle: auth failure (no token / invalid) */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
  private final ObjectMapper objectMapper;

  public JwtAuthenticationEntryPoint(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  @Override
  public void commence(
      HttpServletRequest request,
      HttpServletResponse response,
      @NonNull AuthenticationException authException)
      throws IOException {

    ErrorResponse error =
        new ErrorResponse(
            401,
            "Unauthorized",
            "Missing or invalid token",
            request.getRequestURI(),
            request.getMethod(),
            Collections.emptyList());

    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    response.setContentType("application/json");

    response.getWriter().write(objectMapper.writeValueAsString(error));
    response.getWriter().flush();
  }
}
