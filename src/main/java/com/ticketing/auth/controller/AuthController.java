package com.ticketing.auth.controller;

import com.ticketing.auth.dto.LoginResponse;
import com.ticketing.auth.dto.LoginResult;
import com.ticketing.auth.dto.PasswordResetResponse;
import com.ticketing.auth.service.AuthService;
import com.ticketing.global.api.ApiResponse;
import com.ticketing.global.config.SecurityProperties;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final SecurityProperties securityProperties;

    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(@RequestBody LoginResponse request,
                                            HttpServletResponse response
    ) {

        LoginResult result = authService.login(request);
        ResponseCookie cookie = ResponseCookie.from("accessToken", result.getAccessToken())
                .httpOnly(true)
                .secure(securityProperties.getCookie().isSecure())
                .sameSite(securityProperties.getCookie().getSameSite())
                .path("/")
                .domain("df2njav4b350g.cloudfront.net")
                .maxAge(Duration.ofMinutes(30))
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        return ApiResponse.ok(result.getLoginResponse());
    }


    @PostMapping("/resetPassword")
    public ApiResponse<Void> resetPassword(@RequestBody PasswordResetResponse request) {
        authService.resetPassword(request);
        return ApiResponse.ok(null);
    }


}
