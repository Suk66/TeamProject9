package com.mycompany.teamproject9.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests(auth -> auth
                        .antMatchers("/", "/home", "/login", "/create", "/signup", "/session/info").permitAll() // 로그인 없이 접근 가능
                        .antMatchers("/admin/**").hasRole("ADMIN") // 관리자만 접근 가능
                        .antMatchers("/customer/**").hasRole("CUSTOMER") // 일반회원만 접근 가능
                        .antMatchers("/customer-dashboard").hasRole("CUSTOMER") // 로그인한 사용자 모두 허용
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED) // ✅ 세션이 필요할 때 생성하도록 설정
                        .maximumSessions(1) // ✅ 하나의 세션만 유지 (중복 로그인 방지)
                        .expiredUrl("/login?expired") // ✅ 세션 만료 시 로그인 페이지로 이동
                )
                .formLogin().disable();

        return http.build();
    }
}
