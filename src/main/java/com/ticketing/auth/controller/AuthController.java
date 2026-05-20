package com.ticketing.auth.controller;

import com.ticketing.auth.dto.LoginResponse;
import com.ticketing.auth.dto.PasswordResetResponse;
import com.ticketing.auth.service.AuthService;
import com.ticketing.global.api.ApiResponse;
import com.ticketing.global.config.SecurityProperties;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final SecurityProperties securityProperties;

    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(@RequestBody LoginResponse request
    ) {

        LoginResponse response = authService.login(request);
        //쿠키설정 코드
//        ResponseCookie cookie = ResponseCookie.from("accessToken", result.getAccessToken())
//                .httpOnly(true)
//                .secure(securityProperties.getCookie().isSecure())
//                .sameSite(securityProperties.getCookie().getSameSite())
//                .path("/")
//                .maxAge(Duration.ofMinutes(30))
//                .build();
//        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        return ApiResponse.ok(response);
    }


    @PostMapping("/resetPassword")
    public ApiResponse<Void> resetPassword(@RequestBody PasswordResetResponse request) {
        authService.resetPassword(request);
        return ApiResponse.ok(null);
    }


}
