package com.example.bloghome.comment.repository;

import com.example.bloghome.comment.domain.Comments;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comments, Long> {

    List<Comments> findByArticlesIdOrderByCreatedAtDesc(Long articlesId); // DB에서 정렬을 해서 보내줌

}
