package com.example.bloghome.config.security.filter;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// 너 유저네임이랑 페드워드로 로그인 하는 거지 ?
public class FormLoginFilter extends UsernamePasswordAuthenticationFilter { // 아이디랑 패스워드를 이용한 방식의 필터이다.)
    // JSON 파일을 Object로 변환 도 가능하고 오브젝트에서 제이슨으로도 변환 가능하다.
    private final ObjectMapper objectMapper;

    public FormLoginFilter(final AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
        objectMapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    //
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        // Provider를 관리하는 manger 에게 전달할 토큰 생성
        UsernamePasswordAuthenticationToken authRequest;

        try {
            // RequestBody 에서 username, password 추출한다
            JsonNode requestBody = objectMapper.readTree(request.getInputStream());
            String username = requestBody.get("username").asText();
            String password = requestBody.get("password").asText();
            authRequest = new UsernamePasswordAuthenticationToken(username, password);
        } catch (Exception e) {
            throw new RuntimeException("username, password 입력이 필요합니다. (JSON)");
        }

        // 리퀘스트에 토큰을 담아준다 셋디테일스
        setDetails(request, authRequest);
        return this.getAuthenticationManager().authenticate(authRequest);
    }
}
