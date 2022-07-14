package com.example.bloghome.article.sevice;

import com.example.bloghome.article.repository.ArticleRepository;
import com.example.bloghome.article.domain.Articles;
import com.example.bloghome.article.dto.ArticleSaveDto;
import com.example.bloghome.article.dto.ArticlesDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticlesService {

    private final ArticleRepository articleRepository;

    public ArticlesService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public void writeArticle(ArticleSaveDto dto){
        articleRepository.save(dto.toEntity());
    }

// 리스트로 출력
    public List<ArticlesDto> articles(){
        return articleRepository.findAll()
                .stream()
                .map(ArticlesDto::new)
                .collect(Collectors.toList()); // 리스트라는 박스에 담겠다.

    }

    public ArticlesDto findOne(Long articleId) {
        Articles entity = articleRepository.findById(articleId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));

        return new ArticlesDto(entity);
    }
}
