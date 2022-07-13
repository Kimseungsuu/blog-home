package com.example.bloghome.member.service;

import com.example.bloghome.member.dto.JoinRequestDto;
import com.example.bloghome.member.domain.Member;
import com.example.bloghome.member.repository.MemberRepository;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public String join(JoinRequestDto dto) {
        String password = dto.getPassword();
        String rePassword = dto.getRePassword();
        String nickname = dto.getNickname();

        if (!password.equals(rePassword)) { //비밀번호와 비밀번호 확인이 다르면 실패
            return "회원가입이 실패하셨습니다.";
        }

        if (password.contains(nickname)) { //닉네임과 같은 값이 포함될 경우 회원가입 실패
            return "회원가입이 실패하셨습니다.";
        }

        if (memberRepository.findByNickname(nickname) != null) {
            return "중복된 닉네임이 있습니다.";
        }

        //dto 를 entity 변환
        Member entity = dto.toEntity();
        // 명시적으로 변수를 엔티티로 함 -> 멤버로 해도된다.
        memberRepository.save(entity);
        return "성공";
    }
}
