package com.noticias.portal.models;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.noticias.portal.enums.Rol;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "cuentas")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cuenta {

    @Id
    private String id;
    private String nombre;
    private String dni;
    private String email;
    private String clave; // contraseña hasheada
    private LocalDateTime fechaRegistro;
    private Rol rol;

}
