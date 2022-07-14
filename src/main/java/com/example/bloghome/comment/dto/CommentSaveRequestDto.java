package com.example.bloghome.comment.dto;

import lombok.Data;

@Data
public class CommentSaveRequestDto {

    private String content;
    private String username;
    private Long articlesId;
}
