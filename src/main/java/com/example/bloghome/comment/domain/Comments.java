package com.example.bloghome.comment.domain;

import com.example.bloghome.article.domain.Articles;
import com.example.bloghome.member.domain.Member;
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
@Setter
public class Comments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    //Writer -> 회원이랑 직접 연관지어야 된다.
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    // 댓글 -> 게시글의 댓글
    @ManyToOne
    @JoinColumn(name = "articles_id")
    private Articles articles;

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
    @Column(insertable = false)
    private String updateBy;

    public Comments() {
    }

    private Comments(String content, Member member, Articles articles) {
        this.content = content;
        this.member = member;
        this.articles = articles;

    }

    public static Comments createComments(String content, Member member, Articles articles){
        return new Comments(content, member, articles);
    }
}
