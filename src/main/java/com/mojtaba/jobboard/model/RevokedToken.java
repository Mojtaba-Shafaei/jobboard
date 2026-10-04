package com.mojtaba.jobboard.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "revoked_tokens")
public class RevokedToken {
    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    @Column(unique = true, nullable = false, length = 500)
    private String token;

    @Getter
    @Setter
    @Column(nullable = false)
    private LocalDateTime expiresAt;

    public RevokedToken() {
        // used by JPA
    }

    public RevokedToken(String token, LocalDateTime expiresAt) {
        this.token = token;
        this.expiresAt = expiresAt;
    }
}
