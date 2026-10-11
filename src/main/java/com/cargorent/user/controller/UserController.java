package com.cargorent.user.controller;

import com.cargorent.user.common.CurrentUser;
import com.cargorent.user.dto.ChangePasswordRequest;
import com.cargorent.user.dto.UpdateProfileRequest;
import com.cargorent.user.dto.UserResponse;
import com.cargorent.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public UserResponse me(CurrentUser user) {
        return userService.get(user.requireId());
    }

    @PutMapping("/me")
    public UserResponse updateMe(CurrentUser user, @Valid @RequestBody UpdateProfileRequest req) {
        return userService.updateProfile(user.requireId(), req);
    }

    @PutMapping("/me/password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changePassword(CurrentUser user, @Valid @RequestBody ChangePasswordRequest req) {
        userService.changePassword(user.requireId(), req);
    }

    /** Admin: list all customers and admins. */
    @GetMapping
    public List<UserResponse> list(CurrentUser user) {
        user.requireAdmin();
        return userService.listAll();
    }

    @GetMapping("/{id}")
    public UserResponse get(CurrentUser user, @PathVariable Long id) {
        if (!user.isInternal() && !user.isAdmin() && !id.equals(user.id())) {
            user.requireAdmin();
        }
        return userService.get(id);
    }

    @PatchMapping("/{id}/active")
    public UserResponse setActive(CurrentUser user, @PathVariable Long id, @RequestParam boolean value) {
        user.requireAdmin();
        return userService.setActive(id, value);
    }
}
