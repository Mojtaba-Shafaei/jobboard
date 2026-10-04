package com.mojtaba.jobboard.config.security;

import com.mojtaba.jobboard.model.RevokedToken;
import com.mojtaba.jobboard.repository.RevokedTokenRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * Blocks revoked JWTs (e.g. after logout) until they expire naturally.
 * Expired rows are purged so the table cannot grow unbounded.
 */
@Service
public class TokenInvalidator {
    private final RevokedTokenRepository revokedTokenRepository;
    private final JwtService jwtService;

    public TokenInvalidator(RevokedTokenRepository revokedTokenRepository, JwtService jwtService) {
        this.revokedTokenRepository = revokedTokenRepository;
        this.jwtService = jwtService;
    }

    /** Revokes the given token. The token must be valid and unexpired. */
    @Transactional
    public void revoke(String token) {
        if (revokedTokenRepository.existsByToken(token)) {
            return; // already revoked
        }

        LocalDateTime expiresAt = jwtService.extractExpiration(token).toInstant()
                .atZone(java.time.ZoneId.systemDefault())
                .toLocalDateTime();

        revokedTokenRepository.save(new RevokedToken(token, expiresAt));

        // rows past their expiry can never match a valid token again - drop them
        revokedTokenRepository.deleteByExpiresAtBefore(LocalDateTime.now());
    }

    /** @return true if the token was revoked (e.g. via logout) before it expired. */
    public boolean isRevoked(String token) {
        return revokedTokenRepository.existsByToken(token);
    }
}
