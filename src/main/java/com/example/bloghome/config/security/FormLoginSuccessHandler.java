package com.example.bloghome.config.security;

import com.example.bloghome.config.security.jwt.JwtTokenUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// FormLoginFilter가 성공 했을 때 실행 될 클래스
public class FormLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    public static final String AUTH_HEADER = "Authorization"; // 토큰을 가지고 있으면 해더에 꼭 써줘야된다. Authorization(규칙)
    public static final String TOKEN_TYPE = "BEARER"; // 토큰의 종류 중 하나 BEARER 제일 많이쓰는 유형이다.

    @Override
    public void onAuthenticationSuccess(final HttpServletRequest request, final HttpServletResponse response,
                                        final Authentication authentication) {
        final MemerDetailsImpl userDetails = ((MemerDetailsImpl) authentication.getPrincipal());
        // Token 생성
        final String token = JwtTokenUtils.generateJwtToken(userDetails); // 토큰생성과정
        response.addHeader(AUTH_HEADER, TOKEN_TYPE + " " + token); //한번 리퀘스트가 오면 리스폰스를 한번 쏴줘야된다.
        //response에 토큰을 담아서 주어라
    }

}
