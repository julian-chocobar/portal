package com.noticias.portal.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CuentaDTO {
    private String id;
    private String nombre;
    private String dni;
    private String email;
    private String clave;
    private String fechaRegistro;
    private String rol;
}
