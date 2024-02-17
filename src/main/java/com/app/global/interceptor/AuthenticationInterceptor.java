package com.app.global.interceptor;

import com.app.global.error.ErrorCode;
import com.app.global.error.exception.AuthenticationException;
import com.app.global.jwt.constant.TokenType;
import com.app.global.jwt.service.TokenManager;
import com.app.util.AuthorizationHeaderUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class AuthenticationInterceptor implements HandlerInterceptor {
    private final TokenManager tokenManager;
    //컨트롤러 로직 수행전에 수행되는 부분
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //1. 토큰 Header 검증
        String token = AuthorizationHeaderUtils.validationAuthorization(request);

        //2. 토큰 검증
        tokenManager.validateToken(token);

        //3.토큰 타입
        Claims tokenClaims = tokenManager.getTokenClaims(token);
        String tokenType = tokenClaims.getSubject();
        if (!TokenType.isAccessToken(tokenType)) {
            throw new AuthenticationException(ErrorCode.NOT_ACCESS_TOKEN_TYPE);
        }

        return true;
    }
}
