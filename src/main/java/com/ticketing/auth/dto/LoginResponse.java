package com.ticketing.auth.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LoginResponse {
    private String loginId;
    private String password;
    private String accessToken;
    private int passwordResetCount;

}