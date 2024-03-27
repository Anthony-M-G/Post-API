package com.cr.app.postapi.services;

import com.cr.app.postapi.dtos.CommentDTO;
import org.springframework.http.ResponseEntity;

public interface ICommentsService {

    ResponseEntity<Object> getAllMyComments(Integer page, Integer size, String email);

    ResponseEntity<Object> getAllComments(String email);

    ResponseEntity<String> createComment(CommentDTO commentDTO);

    ResponseEntity<String> updateComment(CommentDTO commentDTO);

    ResponseEntity<String> deleteCommentByCommentId(Long postId);






}
