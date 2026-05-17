package com.ticketing.auth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordResetResponse {
    private String loginId;
    private String newPassword;
}
