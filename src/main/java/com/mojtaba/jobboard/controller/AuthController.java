package com.mojtaba.jobboard.controller;

import com.mojtaba.jobboard.config.security.JwtService;
import com.mojtaba.jobboard.exception.InvalidCredentialsException;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
  private final JwtService jwtService;

  public AuthController(JwtService jwtService) {
    this.jwtService = jwtService;
  }

  @PostMapping("/login")
  public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, String> request) {
    String username = request.get("username");
    String password = request.get("password");

    if (!"admin".equals(username) || !"1234".equals(password)) {
      throw new InvalidCredentialsException("Invalid credentials");
    }

    return ResponseEntity.ok(Map.of("token", jwtService.generateToken(username)));
  }
}
