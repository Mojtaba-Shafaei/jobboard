package com.mojtaba.jobboard.config.security;

import com.mojtaba.jobboard.model.User;
import com.mojtaba.jobboard.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {
  @Bean
  CommandLineRunner initUsers(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    return _ -> {
      if (userRepository.count() == 0) {

        User user1 = new User();
        user1.setUsername("mojtaba");
        user1.setPassword(passwordEncoder.encode("1234"));
        user1.setRole("ROLE_USER");

        User user2 = new User();
        user2.setUsername("admin");
        user2.setPassword(passwordEncoder.encode("admin1234"));
        user2.setRole("ROLE_ADMIN");

        userRepository.save(user1);
        userRepository.save(user2);
      }
    };
  }
}
