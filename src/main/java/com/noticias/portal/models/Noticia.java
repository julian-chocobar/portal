package com.noticias.portal.models;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.noticias.portal.enums.Tema;

import lombok.Builder;
import lombok.Data;

@Document(collection = "noticias")
@Data
@Builder
public class Noticia {

    @Id
    private String id;

    private String titulo;
    private String descripcion;
    private String cuerpo;
    private LocalDateTime fechaPublicacion;
    private Tema tema;
    private List<Foto> fotos;
    private Ubicacion ubicacion;

}
