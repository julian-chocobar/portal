package com.noticias.portal.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.noticias.portal.models.Ubicacion;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

@Service
public class DireccionService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    
    // URL del servicio USIG
    private static final String USIG_API_URL = "http://servicios.usig.buenosaires.gob.ar/normalizar";

    public DireccionService() {
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
    }

    /**
     * Normaliza una dirección utilizando el servicio USIG
     * @param direccion La dirección a normalizar
     * @return Un objeto Ubicacion con la dirección normalizada y sus coordenadas
     */
    public Optional<Ubicacion> normalizarDireccion(String direccion) {
        if (direccion == null || direccion.trim().isEmpty()) {
            return Optional.empty();
        }

        try {
            // Construir la URL con los parámetros requeridos por USIG
            String url = UriComponentsBuilder.fromUriString(USIG_API_URL)
                    .queryParam("direccion", direccion) // UriComponentsBuilder codifica automáticamente
                    .queryParam("geocodificar", "true") // Asegurar que devuelva coordenadas
                    .queryParam("maxOptions", "5") // Limitar a 5 resultados
                    .build()
                    .toUriString();
            
            // Realizar la petición al servicio
            String respuesta = restTemplate.getForObject(url, String.class);
            
            // Procesar la respuesta JSON
            return procesarRespuesta(respuesta);
            
        } catch (Exception e) {
            // Cualquier error en la petición o procesamiento
            System.err.println("Error al normalizar dirección: " + e.getMessage());
            return Optional.empty();
        }
    }

    /**
     * Procesa la respuesta JSON del servicio USIG
     * @param respuestaJson La respuesta JSON del servicio
     * @return Un objeto Ubicacion con la dirección normalizada y sus coordenadas
     */
    private Optional<Ubicacion> procesarRespuesta(String respuestaJson) {
        try {
            JsonNode rootNode = objectMapper.readTree(respuestaJson);
            
            // Verificar si la normalización fue exitosa
            if (rootNode.has("direccionesNormalizadas") && rootNode.get("direccionesNormalizadas").isArray() 
                    && rootNode.get("direccionesNormalizadas").size() > 0) {
                
                JsonNode primeraDireccion = rootNode.get("direccionesNormalizadas").get(0);
                
                // Obtener la dirección normalizada
                String direccionNormalizada = primeraDireccion.get("direccion").asText();
                double latitud = 0.0;
                double longitud = 0.0;
                
                // Verificar si tiene coordenadas
                if (primeraDireccion.has("coordenadas")) {
                    JsonNode coordenadas = primeraDireccion.get("coordenadas");
                    if (coordenadas.has("y") && coordenadas.has("x")) {
                        latitud = coordenadas.get("y").asDouble();
                        longitud = coordenadas.get("x").asDouble();
                    }
                }
                
                // Crear y devolver el objeto Ubicacion
                return Optional.of(Ubicacion.builder()
                        .direccion(direccionNormalizada)
                        .latitud(latitud)
                        .longitud(longitud)
                        .build());
            }
            
            // Si no hay direcciones normalizadas, devolver vacío
            return Optional.empty();
            
        } catch (JsonProcessingException e) {
            // Error al procesar el JSON
            return Optional.empty();
        }
    }
}
