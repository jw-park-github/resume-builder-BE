package com.devdoc.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        final long MAX_AGE_SECS = 3600L; // 브라우저가 사전 요청의 결과를 3600초 동안 캐시할 수 있도록 설정

        registry.addMapping("/**")
                .allowedOrigins("http://dev-doc.co.kr", "https://dev-doc.co.kr") // local에서 실행 시 "http://localhost:3000" 추가
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(MAX_AGE_SECS);
    }
}

/*
- Spring MVC 설정 담당.
- 웹 관련 설정 및 CORS 설정을 통해 Front의 요청을 허용하도록 구성.
 */