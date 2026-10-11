package com.cargorent.user.dto;

import com.cargorent.user.entity.User;

import java.time.LocalDateTime;

public record UserResponse(Long id, String name, String email, String phone, String address,
                           String drivingLicenseNo, String role, boolean active, LocalDateTime createdAt) {

    public static UserResponse from(User u) {
        return new UserResponse(u.getId(), u.getName(), u.getEmail(), u.getPhone(), u.getAddress(),
                u.getDrivingLicenseNo(), u.getRole().name(), u.isActive(), u.getCreatedAt());
    }
}
