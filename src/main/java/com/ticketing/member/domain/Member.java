package com.ticketing.member.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "member")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_pk")
    private long memberPk;

    @Column(name = "login_id", nullable = false, unique = true, length = 50)
    private String loginId;

    @Column(name = "token",nullable = false, unique = true, length = 255)
    private String token;

    @Column(name = "password",nullable = false, length = 255)
    private String password;

    @Builder.Default
    @Column(name = "password_reset_count",nullable = false)
    private int passwordResetCount = 0;

}
