package com.cr.app.postapi.mappers;

import com.cr.app.postapi.dtos.CommentDTO;
import com.cr.app.postapi.entities.Comment;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CommentMapperImpl implements IMapper<CommentDTO, Comment> {

    private final ModelMapper modelMapper;
    @Override
    public CommentDTO entityToDto(Comment entity) {
        return modelMapper.map(entity, CommentDTO.class);
    }

    @Override
    public Comment dtoToEntity(CommentDTO dto) {

        return modelMapper.map(dto, Comment.class);
    }

    @Override
    public Page<CommentDTO> entityToDto(Page<Comment> pageofEntities) {
        return pageofEntities.map((element) -> modelMapper.map(element, CommentDTO.class));
    }

    @Override
    public List<CommentDTO> entityToDto(List<Comment> pageofEntities) {
        return pageofEntities.stream().map((element) -> modelMapper.map(element, CommentDTO.class)).toList();
    }
}
