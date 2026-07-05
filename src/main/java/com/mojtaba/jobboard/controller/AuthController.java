package com.mojtaba.jobboard.controller;

import com.mojtaba.jobboard.config.security.JwtService;
import com.mojtaba.jobboard.dto.auth.AuthResponse;
import com.mojtaba.jobboard.dto.auth.LoginRequest;
import com.mojtaba.jobboard.exception.InvalidCredentialsException;
import com.mojtaba.jobboard.exception.UserNotFoundException;
import com.mojtaba.jobboard.model.User;
import com.mojtaba.jobboard.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
  private final JwtService jwtService;
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public AuthController(
      JwtService jwtService, UserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.jwtService = jwtService;
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @PostMapping("/login")
  public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
    String username = request.getUsername();
    String password = request.getPassword();

    User user =
        userRepository
            .findByUsername(username)
            .orElseThrow(() -> new UserNotFoundException("User not found"));

    if (!passwordEncoder.matches(password, user.getPassword())) {
      throw new InvalidCredentialsException("Invalid credentials");
    }

    String token = jwtService.generateToken(user.getUsername());

    return ResponseEntity.ok(new AuthResponse(token));
  }
}
