package com.example.bloghome.member;

import com.example.bloghome.member.dto.JoinRequestDto;
import com.example.bloghome.member.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MemerController {

    private final MemberService memberService;

    public MemerController(MemberService memberService) {
        this.memberService = memberService;
    }

    //GET POST 의 차이는 서버 [리소스]에 변화를 주느냐 안주느냐
    // 회원가입 API
    @PostMapping("/user/signup")
    public String join(JoinRequestDto dto){
        memberService.join(dto);
        return "redirect:/user/loginView";
    }

    // 회원 로그인 페이지
    @GetMapping("/user/loginView")
    public String login() {
        return "login";
    }

    // 회원 가입 페이지
    @GetMapping("/user/signup")
    public String signup() {
        return "signup";
    }

}
