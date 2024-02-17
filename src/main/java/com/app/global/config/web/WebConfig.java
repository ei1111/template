package com.app.global.config.web;

import com.app.global.interceptor.AdminAuthorizationIntercptor;
import com.app.global.interceptor.AuthenticationInterceptor;
import com.app.global.resolver.memberInfo.MemberInfoArgumentResolver;
import com.navercorp.lucy.security.xss.servletfilter.XssEscapeServletFilter;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterRegistration;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
    private final AuthenticationInterceptor authenticationInterceptor;
    private final AdminAuthorizationIntercptor adminAuthorizationIntercptor;
    private final MemberInfoArgumentResolver memberInfoArgumentResolver;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //허용할 url
        registry.addMapping("/api/**")
                //어떤 origins을 허용할 것인지
                //.allowedOrigins(("*")
                .allowedOrigins("http://localhost:8082")
                //어떤 메소드를 허용할것인지
                .allowedMethods(HttpMethod.GET.name()
                        , HttpMethod.POST.name()
                        , HttpMethod.PUT.name()
                        , HttpMethod.PATCH.name()
                        , HttpMethod.DELETE.name()
                        , HttpMethod.OPTIONS.name()
                )
                .maxAge(3600);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authenticationInterceptor)
                .order(1)
                //적용할 url
                .addPathPatterns("/api/**")
                //적용하지 않을 url
                .excludePathPatterns(
                        "/api/oauth/login"
                        , "/api/logout"
                        , "/api/health");

        registry.addInterceptor(adminAuthorizationIntercptor)
                .order(2)
                //적용할 url
                .addPathPatterns("/api/admin/**");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(memberInfoArgumentResolver);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/swagger-ui/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/swagger-ui/3.52.5/");
    }
}
