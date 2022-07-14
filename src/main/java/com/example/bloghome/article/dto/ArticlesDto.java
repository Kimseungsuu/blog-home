package com.example.bloghome.article.dto;

import com.example.bloghome.article.domain.Articles;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ArticlesDto {

    private Long id;
    private String title;
    private String writer;
    private String content;

    public ArticlesDto(Articles entity){
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.writer = entity.getWriter();
        this.content = entity.getContent();
    }
}
