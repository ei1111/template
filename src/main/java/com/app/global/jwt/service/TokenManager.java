package com.app.global.jwt.service;

import com.app.domain.member.constant.Role;
import com.app.global.error.ErrorCode;
import com.app.global.error.exception.AuthenticationException;
import com.app.global.jwt.constant.GrantType;
import com.app.global.jwt.constant.TokenType;
import com.app.global.jwt.dto.JwtTokenDto;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Slf4j
@RequiredArgsConstructor
public class TokenManager {
    private final String accessTokenExpireationTime;
    private final String refreshTokenExpirationTime;
    //accessToken과 refreshToken을 만들때 사용할 tokenSecrect 값
    private final String tokenSecret;

    public JwtTokenDto createJwtTokenDto(Long memberId, Role role) {
        Date accessTokenExpireTime = createAccessTokenExpireTime();
        Date refreshTokenExpireTime = createRefreshTokenExpireTime();

        String accessToken = createAccessToken(memberId, role, accessTokenExpireTime);
        String refreshToken = createRefreshToken(memberId, refreshTokenExpireTime);

        return JwtTokenDto.builder()
                .grantType(GrantType.BEARER.getType())
                .accessToken(accessToken)
                .accessTokenExpireTime(accessTokenExpireTime)
                .refreshToken(refreshToken)
                .refreshTokenExpiredTime(refreshTokenExpireTime)
                .build();
    }

    public Date createAccessTokenExpireTime() {
        return new Date(System.currentTimeMillis() + Long.parseLong(accessTokenExpireationTime));
    }

    public Date createRefreshTokenExpireTime() {
        return new Date(System.currentTimeMillis() + Long.parseLong(refreshTokenExpirationTime));
    }

    public String createAccessToken(Long memberId, Role role, Date expirationTime){
        return Jwts.builder()
                .setSubject(TokenType.ACCESS.name()) //토큰 제목
                .setIssuedAt(new Date())             //토큰 발급 시간
                .setExpiration(expirationTime)       // 토근 만료시간
                .claim("memberId", memberId)      // 회원 아이디
                .claim("role", role)              // 유저 role
                .signWith(SignatureAlgorithm.HS512, tokenSecret.getBytes(StandardCharsets.UTF_8))  // 토큰 사용 알고리즘
                .setHeaderParam("typ", "JWT")
                .compact();
    }

    //RefreshToken은 많은 정보를 담지 않기 위해 role 제거
    public String createRefreshToken(Long memberId, Date expirationTime){
        return Jwts.builder()
                .setSubject(TokenType.REFRESH.name()) //토큰 제목
                .setIssuedAt(new Date())             //토큰 발급 시간
                .setExpiration(expirationTime)       // 토근 만료시간
                .claim("memberId", memberId)      // 회원 아이디
                .signWith(SignatureAlgorithm.HS512, tokenSecret.getBytes(StandardCharsets.UTF_8))  // 토큰 사용 알고리즘
                .setHeaderParam("typ", "JWT")
                .compact();
    }

    //토큰 검증
    public void validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(tokenSecret.getBytes(StandardCharsets.UTF_8))
                    .parseClaimsJws(token);
        } catch (ExpiredJwtException e) {
            throw new AuthenticationException(ErrorCode.TOKEN_EXPIRED);
        } catch (Exception e) {
            throw new AuthenticationException(ErrorCode.NOT_VALID_TOKEN);
        }
    }

    public Claims getTokenClaims(String token) {
        Claims claims;
        try {
            claims = Jwts.parser().setSigningKey(tokenSecret.getBytes(StandardCharsets.UTF_8))
                    .parseClaimsJws(token).getBody();
        } catch (Exception e) {
            throw new AuthenticationException(ErrorCode.NOT_VALID_TOKEN);
        }
        return claims;
    }
}
