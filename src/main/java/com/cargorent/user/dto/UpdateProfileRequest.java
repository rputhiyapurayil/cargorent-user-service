package com.cargorent.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UpdateProfileRequest(
        @NotBlank @Size(max = 100) String name,
        @NotBlank @Pattern(regexp = "^[0-9+\\- ]{7,20}$", message = "is not a valid phone number") String phone,
        @Size(max = 255) String address,
        @Size(max = 30) String drivingLicenseNo) {
}
