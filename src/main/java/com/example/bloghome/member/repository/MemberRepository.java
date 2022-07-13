package com.example.bloghome.member.repository;

import com.example.bloghome.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    //select member from Member where nickname = ?
    Member findByNickname(String nickname);
}
