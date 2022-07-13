package com.example.bloghome.member.domain;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

//JPA가 관리할 객체
@EntityListeners(AuditingEntityListener.class) // 인서트 되는 시간을 읽어서 값을 넣어주는
@Entity
public class Member {

    @Id
    // ID 값 할당 방법
    // 1. 직접 넣는 방식 (setter, 생성자)
    // 2. JPA나 DB에게 할당 책임을 전가 (@GeneratedValue)

    //DB를 자바처럼 관리하겠다는 페이지!
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 아이덴티티 방식으로 명시
    private long id;

    private String username;
    private String password;
    private String nickname;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(insertable = false)
    private LocalDateTime updatedAt;

    @CreatedBy
    @Column(updatable = false)
    private String createdBy;

    @LastModifiedBy
    @Column(insertable = false) // 업데이트때만 사용할 수 있게
    private String updateBy;

    public Member() {
    }

    public Member(String username, String password, String nickname) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;


    }
}
