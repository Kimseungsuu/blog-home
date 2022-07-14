package com.example.bloghome.config.security.provider;

import com.example.bloghome.config.security.MemerDetailsImpl;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.Resource;

// Repository 에서 가지고 온 Member의 패스워드랑 입력한 패스워드가 같은지 비교
// 로그인 페이지에서 아이디 비밀번호로 로그인 인증 구간
public class FormLoginAuthProvider implements AuthenticationProvider {

    @Resource(name= "memberDetailsServiceImpl")
    private UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder passwordEncoder;

    public FormLoginAuthProvider(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
        // FormLoginFilter 에서 생성된 토큰으로부터 아이디와 비밀번호를 조회함
        String username = token.getName();
        String password = (String) token.getCredentials(); //Credentials이 보통 패스워드만 담긴다.

        // UserDetailsService 를 통해 DB에서 username 으로 사용자 조회
        MemerDetailsImpl memerDetails = (MemerDetailsImpl) userDetailsService.loadUserByUsername(username);

        //passwordEncoder 암호화를 한다. (인코딩되는 시간 정보 + 인코딩 패턴) 절대 같을 수 없기 때문에 matches 시간정보랑 비밀번호만 파악한다.
        if (!passwordEncoder.matches(password, memerDetails.getPassword())) {
            throw new BadCredentialsException(memerDetails.getUsername() + "Invalid password"); // BadCredentialsException
        }

        return new UsernamePasswordAuthenticationToken(memerDetails, null, memerDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}
