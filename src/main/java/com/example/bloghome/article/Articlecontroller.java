package com.example.bloghome.article;

import com.example.bloghome.article.dto.ArticleSaveDto;
import com.example.bloghome.article.dto.ArticlesDto;
import com.example.bloghome.article.sevice.ArticlesService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class Articlecontroller {

    private final ArticlesService articlesService;

    //생성자
    public Articlecontroller(ArticlesService articlesService) {
        this.articlesService = articlesService;
    }

    //게시글

    //출력
    @ResponseBody
    @GetMapping("/api/articles")
    public List<ArticlesDto> articles(){
        return articlesService. articles();
    }

//    //상세조회 JSON형식
//    //api/articles/ ?
//    @GetMapping("/api/articles/{articleId}")
//    public ArticlesDto articles(@PathVariable Long articleId){ //리소스 경로지정
//        return articlesService.findOne(articleId);
//    }

    //상세조회 HTML 형식
    //api/articles/ ?
    @GetMapping("/api/articles/{articleId}")
    public String articles(Model model, @PathVariable Long articleId){
        ArticlesDto dto = articlesService.findOne(articleId);
        model.addAttribute("article", dto);
        return "article";
    }

    //저장
    @PostMapping("/api/articles")
    public String writeArticle(ArticleSaveDto dto){
        articlesService.writeArticle(dto);
        return "redirect:/"; //다른 페이지로 이동 시키기 강제로 (루트페이지로)
    }

}
