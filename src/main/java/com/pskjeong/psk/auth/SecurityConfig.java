package com.pskjeong.psk.auth;

import com.pskjeong.psk.auth.service.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final CustomOAuth2UserService oAuth2UserService;
    private final OAuth2SucessHandler oAuth2SucessHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // csrf disable
        http.csrf().disable();
        // JWT Token을 이용할것이니 session 비활성화
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        // iframe(페이지안에 페이지 삽입) 설정 X => REST API라서
        http.headers().frameOptions().disable();
        // oauth2 로그인을 하면 설정해둔 userService 실행
        http.oauth2Login().userInfoEndpoint().userService(oAuth2UserService);
        // oauth2 로그인이 성공한다면 설정해둔 successHandler 실행
        http.oauth2Login().successHandler(oAuth2SucessHandler);

        return http.build();
    }
}
