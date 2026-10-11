package com.cargorent.user.service;

import com.cargorent.user.common.ApiException;
import com.cargorent.user.dto.*;
import com.cargorent.user.entity.Role;
import com.cargorent.user.entity.User;
import com.cargorent.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository users;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Transactional
    public AuthResponse register(RegisterRequest req) {
        String email = req.email().trim().toLowerCase();
        if (users.existsByEmailIgnoreCase(email)) {
            throw ApiException.conflict("An account with this email already exists");
        }
        User user = new User();
        user.setName(req.name().trim());
        user.setEmail(email);
        user.setPasswordHash(passwordEncoder.encode(req.password()));
        user.setPhone(req.phone().trim());
        user.setAddress(req.address());
        user.setDrivingLicenseNo(req.drivingLicenseNo());
        user.setRole(Role.CUSTOMER);
        users.save(user);
        return authResponse(user);
    }

    public AuthResponse login(LoginRequest req) {
        User user = users.findByEmailIgnoreCase(req.email().trim())
                .filter(u -> passwordEncoder.matches(req.password(), u.getPasswordHash()))
                .orElseThrow(() -> ApiException.unauthorized("Invalid email or password"));
        if (!user.isActive()) {
            throw ApiException.forbidden("This account has been deactivated");
        }
        return authResponse(user);
    }

    public UserResponse get(Long id) {
        return UserResponse.from(find(id));
    }

    @Transactional
    public UserResponse updateProfile(Long id, UpdateProfileRequest req) {
        User user = find(id);
        user.setName(req.name().trim());
        user.setPhone(req.phone().trim());
        user.setAddress(req.address());
        user.setDrivingLicenseNo(req.drivingLicenseNo());
        return UserResponse.from(user);
    }

    @Transactional
    public void changePassword(Long id, ChangePasswordRequest req) {
        User user = find(id);
        if (!passwordEncoder.matches(req.currentPassword(), user.getPasswordHash())) {
            throw ApiException.badRequest("Current password is incorrect");
        }
        user.setPasswordHash(passwordEncoder.encode(req.newPassword()));
    }

    public List<UserResponse> listAll() {
        return users.findAllByOrderByCreatedAtDesc().stream().map(UserResponse::from).toList();
    }

    @Transactional
    public UserResponse setActive(Long id, boolean active) {
        User user = find(id);
        if (user.getRole() == Role.ADMIN && !active) {
            throw ApiException.badRequest("Admin accounts cannot be deactivated");
        }
        user.setActive(active);
        return UserResponse.from(user);
    }

    private User find(Long id) {
        return users.findById(id).orElseThrow(() -> ApiException.notFound("User not found"));
    }

    private AuthResponse authResponse(User user) {
        return new AuthResponse(jwtService.issue(user), "Bearer", jwtService.validitySeconds(), UserResponse.from(user));
    }
}
