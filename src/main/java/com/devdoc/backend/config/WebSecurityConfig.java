package com.devdoc.backend.config;


import com.devdoc.backend.security.JwtAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
	private static final Logger log = LoggerFactory.getLogger(WebSecurityConfig.class);

	private final JwtAuthenticationFilter jwtAuthenticationFilter;

	public WebSecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
		this.jwtAuthenticationFilter = jwtAuthenticationFilter;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
				//.cors((cors) -> cors.configurationSource(this.corsConfigurationSource())) // CORS 설정 비활성화
				.csrf(csrf -> csrf.disable()) // CSRF 보호 비활성화
				.httpBasic(httpBasic -> httpBasic.disable()) // HTTP Basic 인증 비활성화
				.sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 세션 사용 안함
				.authorizeHttpRequests(authorize -> authorize
						.requestMatchers("/", "/auth/**", "/api/kogpt/generate", "/swagger-ui/**", "/v3/api-docs/**").permitAll() // 인증 없이 접근 가능한 경로들
						.anyRequest().authenticated() // 나머지 모든 요청은 인증 필요
				)
				.headers(headers -> headers
						.frameOptions(frameOptions -> frameOptions.sameOrigin())
				);
		// JWT 인증 필터 추가
		http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}
}

/*
- Spring Security 설정을 담당. HTTP 보안, 인증 및 인가 설정 정의.
- JwtAuthenticationFilter를 Spring Security filter chain에 추가하여 요청이 인증 및 인가를 통해 처리되도록 함.
 */