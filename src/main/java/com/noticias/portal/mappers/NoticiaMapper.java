package com.noticias.portal.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.noticias.portal.dtos.NoticiaDTO;
import com.noticias.portal.models.Noticia;

@Mapper(componentModel = "spring")
public interface NoticiaMapper {

    @Mapping(source = "fechaPublicacion", target = "fechaPublicacion", dateFormat = "dd/MM/yyyy HH:mm")
    NoticiaDTO toDTO(Noticia noticia);

    @Mapping(source = "fechaPublicacion", target = "fechaPublicacion", dateFormat = "dd/MM/yyyy HH:mm")
    Noticia toEntity(NoticiaDTO dto);

}
