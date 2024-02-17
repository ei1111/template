package com.app.api.token.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
@Builder
public class AccessTokenResponseDto {
    private String grantType;
    private String accessToken;
    //엑세스 토큰 만료 시간
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private Date accessTokenExpireTime;
    private String refreshToken;
    //리프레시 토큰 만료 시간
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private Date refreshTokenExpiredTime;
}
