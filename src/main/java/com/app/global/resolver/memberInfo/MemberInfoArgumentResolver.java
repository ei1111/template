package com.app.global.resolver.memberInfo;

import com.app.domain.member.constant.Role;
import com.app.global.jwt.service.TokenManager;
import com.app.util.AuthorizationHeaderUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class MemberInfoArgumentResolver implements HandlerMethodArgumentResolver {
    private final TokenManager tokenManager;

    //supportsParameter 리턴 값이 true이면 resolveArgument가 실행
    //supportsParameter 리턴 값이 false이면 resolveArgument가 실행 되지 않음
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean haMemberInfoAnnotation = parameter.hasParameterAnnotation(MemberInfo.class);
        //Class.isAssignableFrom()은 특정 Class가 어떤 클래스/인터페이스를 상속/구현했는지 체크합니다.
        boolean hasMemberInfoDto = MemberInfoDto.class.isAssignableFrom(parameter.getParameterType());

        return haMemberInfoAnnotation && hasMemberInfoDto;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        String token = AuthorizationHeaderUtils.validationAuthorization(request);

        Claims tokenClaims = tokenManager.getTokenClaims(token);
        Long memberId = Long.valueOf((Integer)tokenClaims.get("memberId"));
        String role = (String) tokenClaims.get("role");

        return MemberInfoDto.builder()
                .memberId(memberId)
                .role(Role.from(role))
                .build();
    }
}
