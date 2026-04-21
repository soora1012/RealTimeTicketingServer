package com.ticketing.auth.cotroller;

import com.ticketing.auth.dto.LoginRequest;
import com.ticketing.auth.dto.SignupRequest;
import com.ticketing.auth.dto.TokenResponse;
import com.ticketing.auth.service.AuthService;
import com.ticketing.global.api.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ApiResponse<Void> signup(@Valid @RequestBody SignupRequest req) {
        authService.signup(req);
        return ApiResponse.ok(null);
    }

    @PostMapping("/login")
    public ApiResponse<TokenResponse> login(@Valid @RequestBody LoginRequest req) {
        return ApiResponse.ok(authService.login(req));
    }
}
