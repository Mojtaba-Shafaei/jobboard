package com.mojtaba.jobboard.config.security;

import com.mojtaba.jobboard.exception.ErrorResponse;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Collections;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import tools.jackson.databind.ObjectMapper;

@Configuration
public class SecurityConfig {
  private final JwtAuthFilter jwtAuthFilter;
  private final ObjectMapper objectMapper;
  private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

  public SecurityConfig(
      JwtAuthFilter jwtAuthFilter,
      ObjectMapper objectMapper,
      JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint) {
    this.jwtAuthFilter = jwtAuthFilter;
    this.objectMapper = objectMapper;
    this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
  }

  @Bean
  public AuthenticationEntryPoint authEntryPoint() {
    return (request, response, authException) -> {
      ErrorResponse errorResponse =
          new ErrorResponse(
              401,
              "Unauthorized",
              "Missing or invalid token",
              request.getRequestURI(),
              request.getMethod(),
              Collections.emptyList());
      String json = objectMapper.writeValueAsString(errorResponse);
      System.out.println("json=" + json);
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      response.setContentType("application/json");
      response.getWriter().write(json);
      response.getWriter().flush();
    };
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

    http.csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(
            auth -> auth.requestMatchers("/api/auth/**").permitAll().anyRequest().authenticated())
        .exceptionHandling(
            ex ->
                ex.authenticationEntryPoint(
                    jwtAuthenticationEntryPoint)) // auth failure (no token / invalid)
        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
