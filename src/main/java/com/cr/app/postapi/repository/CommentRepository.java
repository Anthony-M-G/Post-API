package com.cr.app.postapi.repository;

import com.cr.app.postapi.entities.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    Optional<Comment> findByEmail(String email);
    boolean existsByEmail(String email);

    Page<Comment> findAllByEmail(String email, Pageable pageable);

    Optional<Comment> findUserByPostId(Long postId);
}
