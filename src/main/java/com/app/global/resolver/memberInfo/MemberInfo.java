package com.app.global.resolver.memberInfo;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//컨트롤러 클래스에 파라미터를 넣을 경우
@Target(ElementType.PARAMETER)
//런타임까지 유지
@Retention(RetentionPolicy.RUNTIME)
public @interface MemberInfo {
}
