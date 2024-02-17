package com.example.tdd.board.web.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@EnableWebSecurity
public class SpringSecurityConfig {

    private final JwtRequestFilter jwtRequestFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .httpBasic().disable()
            .csrf().disable() // rest api 이므로 csrf 불필요
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 세션 생성 필요하지 않음
            .and()
                .authorizeHttpRequests()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .antMatchers("/swagger-ui/**", "/swagger*/**", "/v2/api-docs", "/configuration/**", "/webjars/**", "/swagger-resources/**").permitAll()
                .antMatchers("/user/**").permitAll() // social login 경로는 모두 허용
                .anyRequest().hasRole("USER")  // 그 외 요청은 로그인한 사용자만 접근 가능
            .and()
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
