package com.example.bloghome.comment.dto;

import com.example.bloghome.comment.domain.Comments;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CommentResponseDto {


    @NotNull
    private Long id;

    @NotNull
    private String conntent;

    @NotNull
    private String username;

    public CommentResponseDto() {
    }

    //커맨츠를 디티오로 바꿔주는 작업
    public CommentResponseDto(Comments comments) {
        this.id = comments.getId();
        this.conntent = comments.getContent();
        this.username = comments.getMember().getUsername();
    }
}
