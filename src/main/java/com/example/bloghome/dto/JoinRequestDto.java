package com.example.bloghome.dto;

import com.example.bloghome.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@NoArgsConstructor //생성자를 하나라도 만들게 되면 기본생성자가 없어지기 떄문에 명시적으로 생성
@AllArgsConstructor // 모든 생성자 (나중에 테스트 코드 작성을 위한 것)
@Getter //스프링이 자동으로 Json-> Class 변경을 위해 게터가 필요
public class JoinRequestDto {

    // id는 고유한 식별자라서 DB에서 많이 쓰기 떄문에 유저네임을 아이디 대신 많이 쓰자
    private String username;
    @Size(min = 4) // 패스워드 4자리 이상으로 설정
    private String password;
    @Size(min = 4)
    private String rePassword;
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d).{3,}$") // 정규표현식을 통해서 닉네임 조건을 만족시킴
    private String nickname;

    public Member toEntity() {
        return new Member(username, password, nickname);
    }


}
