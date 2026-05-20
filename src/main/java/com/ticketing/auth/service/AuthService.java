package com.ticketing.auth.service;

import com.ticketing.auth.dto.LoginResponse;
import com.ticketing.auth.dto.PasswordResetResponse;
import com.ticketing.auth.jwt.JwtTokenProvider;
import com.ticketing.global.error.ApiException;
import com.ticketing.global.error.ErrorCode;
import com.ticketing.member.domain.Member;
import com.ticketing.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;


    @Transactional
    public LoginResponse login(LoginResponse request) {
        Member member = memberRepository.findByLoginId(request.getLoginId())
                .orElseThrow(() -> new ApiException(ErrorCode.LOGIN_FAILED));

        if (!passwordEncoder.matches(request.getPassword(), member.getPassword())) {
            throw new ApiException(ErrorCode.LOGIN_FAILED);
        }
        String token = jwtTokenProvider.createAccessToken(
                member.getMemberPk(),
                "USER"
        );

        return LoginResponse.builder()
                .accessToken(token)
                .loginId(member.getLoginId())
                .passwordResetCount(member.getPasswordResetCount())
                .build();
    }

    @Transactional
    public void resetPassword(PasswordResetResponse request) {
        Member member = memberRepository.findByLoginId(request.getLoginId())
                .orElseThrow(() -> new ApiException(ErrorCode.LOGIN_FAILED));

        String encodedPassword = passwordEncoder.encode(request.getNewPassword());
        member.setPassword(encodedPassword);
        member.setPasswordResetCount(member.getPasswordResetCount() +1);
    }

}
