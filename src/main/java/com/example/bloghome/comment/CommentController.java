package com.example.bloghome.comment;

import com.example.bloghome.comment.dto.CommentDeleteRequestDto;
import com.example.bloghome.comment.dto.CommentResponseDto;
import com.example.bloghome.comment.dto.CommentSaveRequestDto;
import com.example.bloghome.comment.dto.CommentUpdateRequestDto;
import com.example.bloghome.comment.service.CommentService;
import com.example.bloghome.config.security.MemerDetailsImpl;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommentController {

    private final CommentService commentService;

    //생성자
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    //댓글 목록 조회
    @GetMapping("/api/comment/{articlesId}")
    public List<CommentResponseDto> showComments(@PathVariable Long articlesId){
        return commentService.showComments(articlesId);
    }

    //댓글 쓰기
    @PostMapping("/api/comment")
    public void writeComment(@AuthenticationPrincipal MemerDetailsImpl memberDetails,
                             @RequestBody CommentSaveRequestDto dto) {
        dto.setUsername(memberDetails.getMember().getUsername());
        commentService.writeComment(dto);
    }

    //댓글 수정
    @PutMapping("/api/comment")
    public void updateComment(@AuthenticationPrincipal MemerDetailsImpl memberDetails,
                              @RequestBody CommentUpdateRequestDto dto) {
        dto.setUsername(memberDetails.getMember().getUsername());
        commentService.updateComment(dto);
    }

    //댓글 삭제
    @DeleteMapping("/api/comment")
    public void deleteComment(@AuthenticationPrincipal MemerDetailsImpl memberDetails,
                              @RequestBody CommentDeleteRequestDto dto) {
        dto.setUsername(memberDetails.getMember().getUsername());
        commentService.deleteComment(dto);
    }
}
