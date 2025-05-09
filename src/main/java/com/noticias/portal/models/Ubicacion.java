package com.noticias.portal.models;

import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Ubicacion {
    private String direccion;
    private double latitud;
    private double longitud;
}
