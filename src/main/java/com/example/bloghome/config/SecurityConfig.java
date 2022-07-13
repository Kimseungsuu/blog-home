package com.example.bloghome.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;

// Spring Security 관리 파일
@EnableWebFluxSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override // login을 뚫기 위한 세팅
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.cors().disable();
        http.authorizeRequests()
                .anyRequest().permitAll();
    }
}
