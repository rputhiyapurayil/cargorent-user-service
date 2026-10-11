package com.cargorent.user.repository;

import com.cargorent.user.entity.Role;
import com.cargorent.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmailIgnoreCase(String email);
    boolean existsByEmailIgnoreCase(String email);
    boolean existsByRole(Role role);
    List<User> findAllByOrderByCreatedAtDesc();
}
