package com.noticias.portal.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.noticias.portal.dtos.CuentaDTO;
import com.noticias.portal.models.Cuenta;

@Mapper(componentModel = "spring")
public interface CuentaMapper {

    @Mapping(source = "fechaRegistro", target = "fechaRegistro", dateFormat = "dd/MM/yyyy HH:mm")
    @Mapping(source = "rol", target = "rol")
    CuentaDTO toDTO(Cuenta cuenta);

    @Mapping(source = "fechaRegistro", target = "fechaRegistro", dateFormat = "dd/MM/yyyy HH:mm")
    @Mapping(source = "rol", target = "rol")
    Cuenta toEntity(CuentaDTO dto);

    @Mapping(source = "fechaRegistro", target = "fechaRegistro", dateFormat = "dd/MM/yyyy HH:mm")
    @Mapping(source = "rol", target = "rol")
    void updateEntity(CuentaDTO dto, @MappingTarget Cuenta cuenta);

}
