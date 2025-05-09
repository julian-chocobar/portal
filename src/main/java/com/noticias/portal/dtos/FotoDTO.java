package com.noticias.portal.dtos;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FotoDTO {
    private String url;
    private String descripcion;
}
