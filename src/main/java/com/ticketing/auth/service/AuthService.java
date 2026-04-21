package com.ticketing.auth.service;

import com.ticketing.auth.dto.LoginRequest;
import com.ticketing.auth.dto.SignupRequest;
import com.ticketing.auth.dto.TokenResponse;
import com.ticketing.auth.jwt.JwtTokenProvider;
import com.ticketing.global.error.ApiException;
import com.ticketing.global.error.ErrorCode;
import com.ticketing.user.domain.User;
import com.ticketing.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;

    @Transactional
    public void signup(SignupRequest req) {
        if (userRepository.existsByEmail(req.email())) {
            throw new ApiException(ErrorCode.USER_EXISTS);
        }
        User user = User.builder()
                .email(req.email())
                .passwordHash(passwordEncoder.encode(req.password()))

                .role("USER")
                .build();
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public TokenResponse login(LoginRequest req) {
        User user = userRepository.findByEmail(req.email())
                .orElseThrow(() -> new ApiException(ErrorCode.USER_NOT_FOUND));

        if (!passwordEncoder.matches(req.password(), user.getPasswordHash())) {
            throw new ApiException(ErrorCode.INVALID_CREDENTIALS);
        }

        String token = tokenProvider.createAccessToken(user.getId(), user.getRole());
        return new TokenResponse(token);
    }
}
