package com.cargorent.user.config;

import com.cargorent.user.entity.Role;
import com.cargorent.user.entity.User;
import com.cargorent.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/** Creates the first admin account on startup if no admin exists yet. */
@Slf4j
@Component
@RequiredArgsConstructor
public class AdminSeeder implements CommandLineRunner {

    private final UserRepository users;
    private final PasswordEncoder passwordEncoder;

    @Value("${cargorent.admin.email}")
    private String adminEmail;
    @Value("${cargorent.admin.password}")
    private String adminPassword;

    @Override
    public void run(String... args) {
        if (users.existsByRole(Role.ADMIN)) return;
        User admin = new User();
        admin.setName("CarGoRent Admin");
        admin.setEmail(adminEmail.toLowerCase());
        admin.setPasswordHash(passwordEncoder.encode(adminPassword));
        admin.setPhone("0000000000");
        admin.setRole(Role.ADMIN);
        users.save(admin);
        log.info("Seeded admin account {}", adminEmail);
    }
}
