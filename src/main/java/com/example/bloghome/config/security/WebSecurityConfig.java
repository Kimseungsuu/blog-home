package com.example.bloghome.config.security;

import com.example.bloghome.config.security.filter.FormLoginFilter;
import com.example.bloghome.config.security.filter.JwtAuthFilter;
import com.example.bloghome.config.security.jwt.HeaderTokenExtractor;
import com.example.bloghome.config.security.provider.FormLoginAuthProvider;
import com.example.bloghome.config.security.provider.JWTAuthProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity // 스프링 Security 지원을 가능하게 함
@EnableGlobalMethodSecurity(securedEnabled = true) // @Secured 어노테이션 활성화
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final JWTAuthProvider jwtAuthProvider;
    private final HeaderTokenExtractor headerTokenExtractor;

    public WebSecurityConfig(
            JWTAuthProvider jwtAuthProvider,
            HeaderTokenExtractor headerTokenExtractor
    ) {
        this.jwtAuthProvider = jwtAuthProvider;
        this.headerTokenExtractor = headerTokenExtractor;
    }

    @Bean
    public BCryptPasswordEncoder encodePassword() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) {
        auth
                .authenticationProvider(formLoginAuthProvider())
                .authenticationProvider(jwtAuthProvider);
    }

    @Override
    public void configure(WebSecurity web) {
        // h2-console 사용에 대한 허용 (CSRF, FrameOptions 무시)
        web
                .ignoring()
                .antMatchers("/h2-console/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();

        // 서버에서 인증은 JWT로 인증하기 때문에 Session의 생성을 막습니다.
        http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS); // 토큰을 사용하기 떄문에 굳이 세션이 필요없다.

/*
         * 1.
         * UsernamePasswordAuthenticationFilter 이전에 FormLoginFilter, JwtFilter 를 등록합니다.
         * FormLoginFilter : 로그인 인증을 실시합니다.
         * JwtFilter       : 서버에 접근시 JWT 확인 후 인증을 실시합니다.
         */
//        http
//                .addFilterBefore(formLoginFilter(), UsernamePasswordAuthenticationFilter.class)
//                .addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);

        http.authorizeRequests()
                .anyRequest().permitAll(); // 모든요청에 대해서 허가하는 것이기에 지워도 됨
//                .antMatchers("/user/signup","/login").permitAll()
//                .and()
//                // [로그아웃 기능]
//                .logout()
//                // 로그아웃 요청 처리 URL
//                .logoutUrl("/user/logout").permitAll() // 권한없어도 진행할 수 있게끔 permitAll
//                .and()
//                .exceptionHandling(); // 입센션이 터지면 잡아서 처리해라
//                // "접근 불가" 페이지 URL 설정
//                .accessDeniedPage("/forbidden.html");
    }

    @Bean
    public FormLoginFilter formLoginFilter() throws Exception {
        FormLoginFilter formLoginFilter = new FormLoginFilter(authenticationManager());
        formLoginFilter.setFilterProcessesUrl("/user/login"); // 이 유알엘이 들어왔을때 시큐리티에서는 이렇게실행해라
        formLoginFilter.setAuthenticationSuccessHandler(formLoginSuccessHandler());
        formLoginFilter.afterPropertiesSet();
        return formLoginFilter;
    }

    @Bean
    public FormLoginSuccessHandler formLoginSuccessHandler() {
        return new FormLoginSuccessHandler();
    }

    @Bean
    public FormLoginAuthProvider formLoginAuthProvider() {
        return new FormLoginAuthProvider(encodePassword());
    }

    private JwtAuthFilter jwtFilter() throws Exception {
        List<String> skipPathList = new ArrayList<>();

        // Static 정보 접근 허용
        skipPathList.add("GET,/images/**");
        skipPathList.add("GET,/css/**");

        // h2-console 허용
        skipPathList.add("GET,/h2-console/**");
        skipPathList.add("POST,/h2-console/**");

        // 회원 가입 API 허용
        skipPathList.add("GET,/user/**");
        skipPathList.add("POST,/user/signup");

        skipPathList.add("GET,/");
        skipPathList.add("GET,/basic.js");

        skipPathList.add("GET,/favicon.ico");

        FilterSkipMatcher matcher = new FilterSkipMatcher(
                skipPathList,
                "/**"
        );

        JwtAuthFilter filter = new JwtAuthFilter( //JWT 필더
                matcher,
                headerTokenExtractor
        );
        filter.setAuthenticationManager(super.authenticationManagerBean());

        return filter;
    }

    @Bean //의존관계 등록해줘 스프링아 !
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}