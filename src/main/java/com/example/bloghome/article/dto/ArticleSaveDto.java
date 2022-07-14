package com.example.bloghome.article.dto;

import com.example.bloghome.article.domain.Articles;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ArticleSaveDto {

    @NotNull //비어 있으면 안된다. 안정성 확보
    private String title;

    @NotNull
    private String writer;

    @NotNull
    private String content;

    public Articles toEntity() {
        return new Articles(title, writer, content);
    }
}
