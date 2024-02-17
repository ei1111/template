package com.app.domain.member.entity;

import com.app.domain.common.BaseEntity;
import com.app.domain.common.BaseTimeEntity;
import com.app.domain.member.constant.MemberType;
import com.app.domain.member.constant.Role;
import com.app.global.jwt.dto.JwtTokenDto;
import com.app.util.DateTimeUtils;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
//객체의 무분별한 생성을 막기 위해서 access = AccessLevel.PROTECTED 설정
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {
    @Id
    //GenerationType.IDENTITY 기본키 생성 전략을 데이터 베이스에 맡기겠다는 것이다.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private MemberType memberType;

    @Column(updatable = true, length = 50, nullable = false)
    private String email;

    @Column(length = 200)
    private String password;

    @Column(nullable = false, length = 20)
    private String memberName;

    @Column(length = 200)
    private String profile;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private Role role;

    @Column(length = 250)
    private String refreshToken;

    private LocalDateTime tokenExpirationTime;

    public void updateRefreshToken(JwtTokenDto jwtTokenDto) {
        this.refreshToken = jwtTokenDto.getRefreshToken();
        this.tokenExpirationTime = DateTimeUtils.convertToLocalDateTime(jwtTokenDto.getRefreshTokenExpiredTime());
    }

    public void expireRefreshToken(LocalDateTime now) {
        this.tokenExpirationTime = now;
    }
}
