package com.example.bloghome.controller;

import com.example.bloghome.member.dto.JoinRequestDto;
import com.example.bloghome.member.dto.loginRequestDto;
import com.example.bloghome.member.service.MemberService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

//@Controller
@RestController
public class MemerController {

    private final MemberService memberService;

    public MemerController(MemberService memberService) {
        this.memberService = memberService;
    }

    //GET POST 의 차이는 서버 [리소스]에 변화를 주느냐 안주느냐
    // 회원가입 API
    @PostMapping("/join")
    public String join(@RequestBody JoinRequestDto dto){
        return memberService.join(dto);
    }

    // 로그인 API
    @PostMapping("/login")
    public String join(@RequestBody loginRequestDto dto){
        return dto.toString();
    }
}
