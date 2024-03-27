package com.cr.app.postapi.client;

import com.cr.app.postapi.dtos.CommentDTO;
import com.cr.app.postapi.entities.Comment;
import com.cr.app.postapi.mappers.IMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class CommentsClient {
    private final IMapper<CommentDTO, Comment> mapper;

    private final RestTemplate restTemplate;
    private final String apiURI = "https://jsonplaceholder.typicode.com/comments"; // URL de la API externa

    public List<CommentDTO> fetchComments() {
        Comment[] postsArray = restTemplate.getForObject(apiURI, Comment[].class);
        assert postsArray != null;
        return mapper.entityToDto(Arrays.asList(postsArray));
    }

    // Método para obtener un post por su ID desde la API externa
    public Optional<CommentDTO> fetchCommentByPostId(Long postId) {
        try {
            Comment comment = restTemplate.getForObject(apiURI + "?postId=" + postId, Comment.class);
            return Optional.ofNullable(mapper.entityToDto(comment));
        } catch (Exception e) {
            // Manejo de errores, por ejemplo, si el post con el ID especificado no existe
            return Optional.empty();
        }
    }
    public String createComment(Comment comment){
        try {
            restTemplate.postForEntity(apiURI, comment, Comment.class);
            return "Ingresado correctamente"+comment.toString();
        } catch (RestClientException e) {
            return "No se puedo ingresar";
        }
    }
    public Object updateComment(Comment comment){

        try {
            restTemplate.put(apiURI+"/"+comment.getPostId(), Comment.class);
            return comment;
        } catch (RestClientException e) {
            log.info("El problema fue aquí");
            throw new RuntimeException(e);
        }
    }



}
