package com.app.util;

import com.app.global.error.ErrorCode;
import com.app.global.error.exception.AuthenticationException;
import com.app.global.jwt.constant.GrantType;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.util.StringUtils;

public class AuthorizationHeaderUtils {
    public static String validationAuthorization(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        // 1.authorizationHeader 필수 체크
        if (!StringUtils.hasText(authorizationHeader)) {
            throw new AuthenticationException(ErrorCode.NOT_EXISTS_AUTHORIZATION);
        }

        // 2.authorizationHeader Bearer 체크
        String[] authorizations = authorizationHeader.split(" ");

        if (authorizations.length < 2 || (!GrantType.BEARER.getType().equals(authorizations[0]))) {
            throw new AuthenticationException(ErrorCode.NOT_VALID_BEARER_GRANT_TYPE);
        }
        //토큰 반환
        return authorizations[1];
    }
}
