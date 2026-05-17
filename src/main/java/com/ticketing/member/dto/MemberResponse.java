package com.ticketing.member.dto;

import com.ticketing.member.domain.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberResponse {

    private final String loginId;
    private final String token;
    private final int passwordResetCount;

    public static MemberResponse from(Member member) {
        return MemberResponse.builder()
                .loginId(member.getLoginId())
                .token(member.getToken())
                .passwordResetCount( member.getPasswordResetCount())
                .build();
    }
}