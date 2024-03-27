package com.cr.app.postapi.services.impl;

import com.cr.app.postapi.client.CommentsClient;
import com.cr.app.postapi.dtos.CommentDTO;
import com.cr.app.postapi.entities.Comment;
import com.cr.app.postapi.mappers.IMapper;
import com.cr.app.postapi.repository.CommentRepository;
import com.cr.app.postapi.services.ICommentsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentServicesImpl implements ICommentsService {
    private final IMapper<CommentDTO, Comment> mapper;
    private final CommentRepository commentRepository;
    private final CommentsClient commentsClient;

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<Object> getAllMyComments(Integer page, Integer size, String email) {
        Pageable pageable = PageRequest.of(page, size);
        if (email.isEmpty())
            return new ResponseEntity<>(mapper.entityToDto(commentRepository.findAll(pageable)), HttpStatus.OK);
        else if (commentRepository.existsByEmail(email))
            return new ResponseEntity<>(mapper
                    .entityToDto(commentRepository
                            .findAllByEmail(email, pageable)), HttpStatus.OK);
        return new ResponseEntity<>("No se encontraron post de usuario con email: " + email, HttpStatus.NOT_FOUND);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<Object> getAllComments(String email) {
        List<CommentDTO> myComments = mapper.entityToDto(commentRepository.findAll());
        List<CommentDTO> commentDTO = new ArrayList<>(commentsClient.fetchComments());
        boolean addAll = commentDTO.addAll(myComments);
        if (email.isEmpty() && addAll)
            return new ResponseEntity<>(commentDTO, HttpStatus.OK);
        else if (commentDTO.stream().anyMatch(e -> Objects.equals(e.getEmail(), email)) && addAll)
            return new ResponseEntity<>(commentDTO
                    .stream()
                    .filter(comment -> Objects.equals(comment.getEmail(), email)), HttpStatus.OK);
        return new ResponseEntity<>("No se encontraron post de usuario con email: " + email, HttpStatus.NOT_FOUND);
    }

    @Override
    @Transactional(readOnly = false)
    public ResponseEntity<String> createComment(CommentDTO commentDTO) {
        if (commentDTO.getPostId() <= 0)
            return new ResponseEntity<>("El numero debe ser mayor a cero", HttpStatus.BAD_REQUEST);
        Comment comment = mapper.dtoToEntity(commentDTO);
        commentRepository.save(comment);
        String createComment = commentsClient.createComment(comment);
        return new ResponseEntity<>(createComment, HttpStatus.ACCEPTED);
    }

    @Override
    @Transactional(readOnly = false)
    public ResponseEntity<String> updateComment(CommentDTO commentDTO) {
        if (!commentRepository.existsByEmail(commentDTO.getEmail()))
            return new ResponseEntity<>("El comentario de email " + commentDTO.getEmail() + " no te pertenece", HttpStatus.BAD_REQUEST);
        Optional<Comment> postOptional = commentRepository.findUserByPostId(commentDTO.getPostId());
        if (postOptional.isEmpty())
            return new ResponseEntity<>("No se puede actualizar el registro el post indicado no existe", HttpStatus.BAD_REQUEST);
        postOptional.get().setPostId(commentDTO.getPostId());
        postOptional.get().setName(commentDTO.getName());
        postOptional.get().setEmail(commentDTO.getEmail());
        postOptional.get().setBody(commentDTO.getBody());
        commentRepository.save(postOptional.get());
        return new ResponseEntity<>("Tu comentario fue actualizado correctamente", HttpStatus.ACCEPTED);
    }

    @Override
    @Transactional(readOnly = false)
    public ResponseEntity<String> deleteCommentByCommentId(Long postId) {
        Optional<Comment> postOptional = commentRepository.findUserByPostId(postId);
        if (postOptional.isEmpty())
            return new ResponseEntity<>("No se puede eliminar el registro el post indicado no existe", HttpStatus.BAD_REQUEST);
        commentRepository.delete(postOptional.get());
        return new ResponseEntity<>("Tu comentario fue eliminado correctamente", HttpStatus.ACCEPTED);
    }


}
