package com.ticketing.member.dto;

import com.ticketing.member.domain.Member;
import lombok.Getter;

@Getter
public class MemberResponse {

    private final String userId;
    private final String token;
    private final int passwordResetCount;

    private MemberResponse(String userId, String token, int passwordResetCount) {
        this.userId = userId;
        this.token = token;
        this.passwordResetCount = passwordResetCount;
    }

    public static MemberResponse from(Member member) {
        return new MemberResponse(
                member.getMemberId(),
                member.getToken(),
                member.getPasswordResetCount()
        );
    }
}