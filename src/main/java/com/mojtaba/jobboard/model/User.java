package com.mojtaba.jobboard.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
public class User {
  @Getter
  @Setter
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Getter
  @Setter
  @Column(unique = true, nullable = false)
  private String username;

  @Getter
  @Setter
  @Column(nullable = false)
  private String password; // hashed

  @Getter
  @Setter
  @Column(nullable = false)
  private String role; // e.g. ROLE_USER
}
