package com.noticias.portal.dtos;

import java.util.List;

import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoticiaDTO {
    private String id;
    private String titulo;
    private String descripcion;
    private String cuerpo;
    private String fechaPublicacion; // como String si lo quieres formateado
    private String tema;
    private List<FotoDTO> fotos;
    private UbicacionDTO ubicacion;
}
