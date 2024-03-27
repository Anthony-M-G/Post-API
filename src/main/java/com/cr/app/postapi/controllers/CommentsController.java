package com.cr.app.postapi.controllers;

import com.cr.app.postapi.dtos.CommentDTO;
import com.cr.app.postapi.services.ICommentsService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/v1/app/comments")
public class CommentsController {

    private final ICommentsService iCommentsService;

    @GetMapping("/all")
    public ResponseEntity<Object> getAllComments(@RequestParam(defaultValue = "") @Email @Valid String email){
        return iCommentsService.getAllComments(email);
    }

    @GetMapping("/all/my")
    public ResponseEntity<Object> getAllMyComments(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size, @RequestParam(required = false,defaultValue = "") String email){
        return iCommentsService.getAllMyComments(page, size, email);
    }

    @PostMapping("/create")
    public ResponseEntity<String> createPost(@RequestBody(required = true) @Valid CommentDTO dto){
        return iCommentsService.createComment(dto);
    }

    @PutMapping("/update")
    public ResponseEntity<String> updatePost(@RequestBody @Valid CommentDTO dto){
        return iCommentsService.updateComment(dto);
    }




}

