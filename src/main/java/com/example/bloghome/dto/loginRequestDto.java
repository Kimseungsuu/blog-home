package com.example.bloghome.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor //생성자를 하나라도 만들게 되면 기본생성자가 없어지기 떄문에 명시적으로 생성
@AllArgsConstructor // 모든 생성자 (나중에 테스트 코드 작성을 위한 것)
@Getter //스프링이 자동으로 Json-> Class 변경을 위해 게터가 필요
public class loginRequestDto {
    private String username;
    private String password;
}
