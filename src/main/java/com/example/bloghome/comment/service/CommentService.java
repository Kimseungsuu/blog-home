package com.example.bloghome.comment.service;

import com.example.bloghome.article.domain.Articles;
import com.example.bloghome.article.repository.ArticleRepository;
import com.example.bloghome.comment.repository.CommentRepository;
import com.example.bloghome.comment.domain.Comments;
import com.example.bloghome.comment.dto.CommentDeleteRequestDto;
import com.example.bloghome.comment.dto.CommentResponseDto;
import com.example.bloghome.comment.dto.CommentSaveRequestDto;
import com.example.bloghome.comment.dto.CommentUpdateRequestDto;
import com.example.bloghome.member.domain.Member;
import com.example.bloghome.member.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final ArticleRepository articleRepository;

//생성자
    public CommentService(CommentRepository commentRepository, MemberRepository memberRepository, ArticleRepository articleRepository) {
        this.commentRepository = commentRepository;
        this.memberRepository = memberRepository;
        this.articleRepository = articleRepository;
    }

    //댓글 목록 조회
      public List<CommentResponseDto> showComments(Long articlesId){
//        //모든 게시글의 모든 댓글을 불러오기
//        commentRepository.findAll();

          //특정 게시글의 모든 댓글 불러오기
          // ID의 내림차순으로 하거나 크리티브엣의 기준으로 하는 방법이 있는데 DB에서 정렬을 해서 가지고 옴
            return commentRepository.findByArticlesIdOrderByCreatedAtDesc(articlesId)
                  .stream()
                  .map(CommentResponseDto::new) //생성자에 커맨트 넣는 작업
                  .collect(Collectors.toList());

    }

    //댓글 작성
      public void writeComment(CommentSaveRequestDto dto) {
          Member member = memberRepository.findByUsername(dto.getUsername())
                  .orElseThrow(() -> new IllegalArgumentException("없는 회원입니다."));

          Articles articles = articleRepository.findById(dto.getArticlesId())
                  .orElseThrow(() -> new IllegalArgumentException("잘못된 게시글입니다."));

          //케멘츠에 저장
          commentRepository.save(Comments.createComments(dto.getContent(), member,articles));
    }

        //댓글 수정
       public void updateComment(CommentUpdateRequestDto dto) {
           Comments comments = commentRepository.findById(dto.getId())
                   .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 댓글입니다."));

           //댓글을 쓴 사람인가를 검증해야된다.
           if(!dto.getUsername().equals(comments.getMember().getUsername())){
               throw new IllegalArgumentException("수정 권한이 없습니다."); //댓글 수정권한을 비교해서 수정이 가능한것인지 검증한다.
           }

           comments.setContent(dto.getContent()); //더티체크
       }

       //댓글 삭제
       public void deleteComment(CommentDeleteRequestDto dto) {
           Comments comments = commentRepository.findById(dto.getId())
                   .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 댓글입니다."));

           if(!dto.getUsername().equals(comments.getMember().getUsername())){
               throw new IllegalArgumentException("수정 권한이 없습니다.");
           }
        commentRepository.deleteById(dto.getId());
    }

}
