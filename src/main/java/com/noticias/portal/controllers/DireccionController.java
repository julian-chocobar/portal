package com.noticias.portal.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.noticias.portal.models.Ubicacion;
import com.noticias.portal.services.DireccionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/direcciones")
@RequiredArgsConstructor
public class DireccionController {

    private final DireccionService direccionService;

    /**
     * Normaliza una dirección utilizando el servicio USIG
     * @param direccion La dirección a normalizar
     * @return Un objeto JSON con la dirección normalizada y sus coordenadas
     */
    @GetMapping("/normalizar")
    public ResponseEntity<?> normalizarDireccion(@RequestParam String direccion) {
        // Validar que la dirección no esté vacía
        if (direccion == null || direccion.trim().isEmpty()) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("mensaje", "La dirección no puede estar vacía");
            return ResponseEntity.ok(errorResponse);
        }
        
        // Sugerencia para el formato de dirección según USIG
        if (!direccion.contains(",") && !direccion.contains(" y ")) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("mensaje", "Formato de dirección incorrecto. Intente con 'calle altura, partido' o 'calle y calle, partido'");
            errorResponse.put("ejemplos", new String[]{
                "Callao y Corrientes, CABA",
                "Av. Rivadavia 1000, CABA",
                "Santa Fe y Coronel Díaz"
            });
            return ResponseEntity.ok(errorResponse);
        }
        
        Optional<Ubicacion> ubicacionOpt = direccionService.normalizarDireccion(direccion);
        
        if (ubicacionOpt.isPresent()) {
            Ubicacion ubicacion = ubicacionOpt.get();
            
            // Verificar que las coordenadas sean válidas
            if (ubicacion.getLatitud() == 0.0 && ubicacion.getLongitud() == 0.0) {
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("success", false);
                errorResponse.put("mensaje", "La dirección se normalizó pero no se pudieron obtener coordenadas");
                errorResponse.put("direccionNormalizada", ubicacion.getDireccion());
                return ResponseEntity.ok(errorResponse);
            }
            
            Map<String, Object> response = new HashMap<>();
            response.put("direccion", ubicacion.getDireccion());
            response.put("latitud", ubicacion.getLatitud());
            response.put("longitud", ubicacion.getLongitud());
            response.put("success", true);
            
            return ResponseEntity.ok(response);
        } else {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("mensaje", "No se pudo normalizar la dirección proporcionada");
            errorResponse.put("sugerencia", "Intente con el formato 'calle altura, partido' o 'calle y calle, partido'");
            errorResponse.put("ejemplos", new String[]{
                "Callao y Corrientes, CABA",
                "Av. Rivadavia 1000, CABA",
                "Santa Fe y Coronel Díaz"
            });
            
            return ResponseEntity.ok(errorResponse);
        }
    }
}
