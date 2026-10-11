package com.cargorent.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
        @NotBlank @Size(max = 100) String name,
        @NotBlank @Email @Size(max = 150) String email,
        @NotBlank @Size(min = 6, max = 72, message = "must be 6 to 72 characters") String password,
        @NotBlank @Pattern(regexp = "^[0-9+\\- ]{7,20}$", message = "is not a valid phone number") String phone,
        @Size(max = 255) String address,
        @Size(max = 30) String drivingLicenseNo) {
}
