package com.mojtaba.jobboard.config.security;

import com.mojtaba.jobboard.exception.ErrorResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import tools.jackson.databind.ObjectMapper;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
  private final JwtService jwtService;
  private final ObjectMapper objectMapper;

  public JwtAuthFilter(JwtService jwtService, ObjectMapper objectMapper) {
    this.jwtService = jwtService;
    this.objectMapper = objectMapper;
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    /* exclude URLs that started with "api/auth" e.g. "/api/auth/login"
       description: login page does not need the token/authorization
    */
    String path = request.getRequestURI();
    if (path.startsWith("/api/auth")) {
      filterChain.doFilter(request, response);
      return;
    }

    String authHeader = request.getHeader("Authorization");
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      filterChain.doFilter(request, response);
      return;
    }

    String token = authHeader.substring(7);

    try {
      String username = jwtService.extractUsername(token);

      if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
        UsernamePasswordAuthenticationToken auth =
            new UsernamePasswordAuthenticationToken(
                username, null, List.of(new SimpleGrantedAuthority("USER")));
        SecurityContextHolder.getContext().setAuthentication(auth);
      }

    } catch (Exception ex) {
      SecurityContextHolder.clearContext();

      ErrorResponse errorResponse =
          new ErrorResponse(
              401,
              "Unauthorized",
              "Invalid token",
              request.getRequestURI(),
              request.getMethod(),
              Collections.emptyList());

      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      response.setContentType("application/json");
      response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
      response.getWriter().flush();
      return;
    }

    filterChain.doFilter(request, response);
  }
}
