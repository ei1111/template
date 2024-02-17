/*package com.app.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.OAS_30)
                .select() //ApiSelectorBuilder 생성
                .apis(RequestHandlerSelectors.basePackage("com.app.api"))// API 문서의 경로, 패키지 경로
                //.paths(PathSelectors.any()) //어떤 url을 문서화를 할것인지, any()-> 모든 url에 대해서 문서화
                .paths(PathSelectors.ant("/api/**")) //ant() -> 지정된 api 문서화
                .build();
    }
}*/
