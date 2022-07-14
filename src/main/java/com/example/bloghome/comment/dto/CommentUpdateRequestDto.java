package com.example.bloghome.comment.dto;

import lombok.Data;

@Data
public class CommentUpdateRequestDto {

    private Long id;
    private String content;
    private String username;

}
