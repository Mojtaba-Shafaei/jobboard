package com.mojtaba.jobboard.config.security;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

@Service
public class JwtService {
    private final String SECRET = "my-secret-key-my-secret-key-my-secret-key";

    private Key getKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(getKey())
                .compact();
    }

    public String extractUsername(String token) {
        return Jwts.parserBuilder() //TODO: upgrade this library to the latest version
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
