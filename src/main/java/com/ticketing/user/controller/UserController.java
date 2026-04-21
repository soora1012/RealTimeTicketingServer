package com.ticketing.user.controller;

import com.ticketing.global.api.ApiResponse;
import com.ticketing.user.dto.UserResponse;
import com.ticketing.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public ApiResponse<UserResponse> me(Authentication authentication) {
        return ApiResponse.ok(userService.me((String) authentication.getPrincipal()));
    }
}
