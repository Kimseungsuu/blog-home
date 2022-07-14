package com.example.bloghome.member.service;

import com.example.bloghome.member.domain.Member;
import com.example.bloghome.member.dto.JoinRequestDto;
import com.example.bloghome.member.repository.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    public MemberService(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public String join(JoinRequestDto dto) {
        String password = dto.getPassword();
        String rePassword = dto.getRePassword();
        String nickname = dto.getNickname();

        if (!password.equals(rePassword)) { //비밀번호와 비밀번호 확인이 다르면 실패
            throw new IllegalArgumentException("비밀번호 재확인이 일치 하지 않습니다.");
        }

        if (password.contains(nickname)) { //닉네임과 같은 값이 포함될 경우 회원가입 실패
            throw new IllegalArgumentException("비밀번호에 닉네임이 포함되어 있지 않습니다.");
        }

        if (memberRepository.findByNickname(nickname) != null) {
            throw new IllegalArgumentException("닉네임이 중복되었습니다.");
        }

        //dto 를 entity 변환
        Member member = dto.toEntity();
        member.setPassword(passwordEncoder.encode(member.getPassword()));
        // 명시적으로 변수를 엔티티로 함 -> 멤버로 해도된다.
        memberRepository.save(member);
        return "성공";
    }
}
