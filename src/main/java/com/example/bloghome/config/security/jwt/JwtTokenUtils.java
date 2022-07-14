package com.example.bloghome.config.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.bloghome.config.security.MemerDetailsImpl;

import java.util.Date;

public final class JwtTokenUtils {

    //초단위로 써야되서 쓰는거다
    private static final int SEC = 1;
    private static final int MINUTE = 60 * SEC;
    private static final int HOUR = 60 * MINUTE;
    private static final int DAY = 24 * HOUR;

    // JWT 토큰의 유효기간: 3일 (단위: seconds)
    private static final int JWT_TOKEN_VALID_SEC = 3 * DAY; //서비스정책에 따라 달라질 수 있다. 1,2,3 정책에 따라 데이스가 바뀜
    // JWT 토큰의 유효기간: 3일 (단위: milliseconds)
    private static final int JWT_TOKEN_VALID_MILLI_SEC = JWT_TOKEN_VALID_SEC * 1000;

    public static final String CLAIM_EXPIRED_DATE = "EXPIRED_DATE";
    public static final String CLAIM_USER_NAME = "USER_NAME";
    public static final String JWT_SECRET = "jwt_secret_!@#$%";

    public static String generateJwtToken(MemerDetailsImpl userDetails) {
        String token = null;
        try {
            token = JWT.create()
                    .withIssuer("sparta")   // 이 토큰은 누가 만든건가? 누가 발행했는지를 체크하는 것
                    .withClaim(CLAIM_USER_NAME, userDetails.getUsername()) //토큰의 설정 정보
                     // 토큰 만료 일시 = 현재 시간 + 토큰 유효기간)
                    .withClaim(CLAIM_EXPIRED_DATE, new Date(System.currentTimeMillis() + JWT_TOKEN_VALID_MILLI_SEC))
                    .sign(generateAlgorithm()); //토큰을 완성하는 것 (어떤알고리즘으로 암호화를 할것이나?)
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return token;
    }

    private static Algorithm generateAlgorithm() { // Algorithm.HMAC256 알고리즘으로 암호화 알고리즘은 바꿀수있음
        return Algorithm.HMAC256(JWT_SECRET);
    }
}
