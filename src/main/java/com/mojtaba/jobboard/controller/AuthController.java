package com.mojtaba.jobboard.controller;

import com.mojtaba.jobboard.config.security.JwtService;
import com.mojtaba.jobboard.config.security.TokenInvalidator;
import com.mojtaba.jobboard.dto.auth.AuthResponse;
import com.mojtaba.jobboard.dto.auth.LoginRequest;
import com.mojtaba.jobboard.exception.InvalidCredentialsException;
import com.mojtaba.jobboard.exception.UnauthorizedException;
import com.mojtaba.jobboard.exception.UserNotFoundException;
import com.mojtaba.jobboard.model.User;
import com.mojtaba.jobboard.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
  private final JwtService jwtService;
  private final TokenInvalidator tokenInvalidator;
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public AuthController(
      JwtService jwtService,
      TokenInvalidator tokenInvalidator,
      UserRepository userRepository,
      PasswordEncoder passwordEncoder) {
    this.jwtService = jwtService;
    this.tokenInvalidator = tokenInvalidator;
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

  /** Logs out by revoking the presented token; later requests with it get 401. */
  @PostMapping("/logout")
  public ResponseEntity<Void> logout(
      @RequestHeader(value = "Authorization", required = false) String authHeader) {
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      throw new UnauthorizedException("Missing Authorization header");
    }

    String token = authHeader.substring(7);

    try {
      jwtService.extractUsername(token); // must be a valid, unexpired token
    } catch (Exception ex) {
      throw new UnauthorizedException("Invalid token");
    }

    tokenInvalidator.revoke(token);

    return ResponseEntity.noContent().build();
  }
}
