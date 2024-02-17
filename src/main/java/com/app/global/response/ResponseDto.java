package com.app.global.response;

import com.app.global.jwt.dto.JwtTokenDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Builder
public class ResponseDto {
    private String accessToken;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm::ss", timezone = "Asia/Seoul")
    private Date accessTokenExpireTime;
    private String refreshToken;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm::ss", timezone = "Asia/Seoul")
    private Date refreshTokenExpireTime;

    public static ResponseDto of(JwtTokenDto jwtTokenDto) {
        return ResponseDto.builder()
                .accessToken(jwtTokenDto.getAccessToken())
                .accessTokenExpireTime(jwtTokenDto.getAccessTokenExpireTime())
                .refreshToken(jwtTokenDto.getRefreshToken())
                .refreshTokenExpireTime(jwtTokenDto.getRefreshTokenExpiredTime())
                .build();
    }

}
