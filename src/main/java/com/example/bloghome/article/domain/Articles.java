package com.example.bloghome.article.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter// 만드는 것을 추천하지 않지만 공부용
@AllArgsConstructor
public class Articles {

    @Id // ID 매핑 전략 1. 저장할때 직접 입력 2. DB한테 떠넘기기
    @GeneratedValue(strategy = GenerationType.IDENTITY) // DB한테 떠넘기기 방법 중 하나인 아이덴티티
    private Long id; //범위가 넓은 롱을 쓴다.
    private String title;
    private String writer;
    private String content;

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

    public Articles(String title, String writer, String content) {
        this.title = title;
        this.writer = writer;
        this.content = content;
    }

    public Articles() {
    }
}
