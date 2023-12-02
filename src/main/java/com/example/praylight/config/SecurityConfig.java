package com.example.praylight.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
//                .requiresChannel()  // 채널 보안 설정 시작
//                .anyRequest()       // 모든 요청에 대해서
//                .requiresSecure()   // HTTPS를 필요로 함
//                .and()
                .csrf().disable()
                .authorizeRequests()
                .anyRequest().permitAll()  // Allow access without authentication
//                .anyRequest().authenticated()     // 인증을 필요로 함
                .and()
                .formLogin()          // 폼 로그인 설정
                .and()
                .httpBasic();         // HTTP Basic 인증 설정
    }
}

