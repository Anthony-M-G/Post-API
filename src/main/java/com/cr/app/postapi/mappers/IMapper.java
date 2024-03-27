package com.cr.app.postapi.mappers;

import org.springframework.data.domain.Limit;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IMapper <D,E>{
    D entityToDto(E entity);
    E dtoToEntity(D dto);

    Page<D> entityToDto(Page<E> pageofEntities);
    List<D> entityToDto(List<E> pageofEntities);

}
