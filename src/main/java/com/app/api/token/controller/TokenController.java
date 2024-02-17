package com.app.api.token.controller;

import com.app.api.token.dto.AccessTokenResponseDto;
import com.app.api.token.service.TokenService;
import com.app.util.AuthorizationHeaderUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class TokenController {
    private final TokenService tokenService;

    @PostMapping("/access-token/issue")
    public ResponseEntity<AccessTokenResponseDto> createAccessToken(HttpServletRequest request) {
        String refreshToken =  AuthorizationHeaderUtils.validationAuthorization(request);
        AccessTokenResponseDto accessTokenResponseDto = tokenService.createAccessTokenByRefreshToken(refreshToken);

        return ResponseEntity.ok(accessTokenResponseDto);

    }
}
