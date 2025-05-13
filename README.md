# Portal de Noticias (Demo)

Este proyecto es una pequeña demo que implementa las funcionalidades mínimas requeridas para un portal de noticias:

- Permitir la creación de noticias por parte de un administrador autenticado.
- Permitir la consulta y visualización de noticias por parte de los visitantes del portal.
- Integrar la interacción con sistemas externos de normalización de direcciones y visualización geográfica en mapas.

### Características principales:
- Visualización de noticias con filtros por tema y búsqueda por título.
- Vista en detalle de cada noticia, incluyendo mapa interactivo si la noticia tiene ubicación.
- Administración de noticias (crear/eliminar) mediante autenticación.
- Normalización de direcciones usando USIG - Normalizador de Direcciones v2.1.2.
- Visualización de ubicaciones en mapas usando Leaflet.js.
- Persistencia de datos en MongoDB.

## Tecnologías utilizadas:
- Backend: Spring Boot (Java), Maven Wrapper (mvnw)
- Vista: Thymeleaf (plantillas HTML procesadas en el servidor)
- Base de datos: MongoDB
- Servicios externos:
    - USIG - Normalizador de Direcciones v2.1.2
    - Leaflet.js para mapas interactivos

## Instalación y ejecución

1. Clona el repositorio

```bash
    git clone https://github.com/julian-chocobar/portal.git
```

2. Levanta MongoDB (puedes usar docker)
```bash
    docker run -d --name mongo-portal -p 27017:27017 -e MONGO_INITDB_ROOT_USERNAME=admin -e MONGO_INITDB_ROOT_PASSWORD=admin123 mongo
```

3. Ejecuta el proyecto
```bash
    ./mvnw spring-boot:run
```
La aplicación estará disponible en http://localhost:8080

### Casos de uso mínimos implementados
- Administrador: Puede crear y eliminar noticias. Al crear una noticia con dirección, puede normalizar la dirección utilizando el servicio USIG para obtener coordenadas precisas.
- Visitante: Puede consultar, filtrar y buscar noticias. Si la noticia tiene ubicación, puede visualizarla en un mapa interactivo.

### Integración con servicios externos
#### 1. Visualización de mapas con Leaflet.js
Cuando una noticia tiene una ubicación asociada, en la vista de detalle se muestra un mapa interactivo usando Leaflet.js. El mapa marca la posición de la noticia y permite al usuario visualizar el entorno.
- El código de integración se encuentra en la plantilla correspondiente (por ejemplo, noticias/ver.html).
- Leaflet se carga desde CDN y utiliza las coordenadas almacenadas en la base de datos.

#### 2. Normalización de direcciones con USIG
#### DireccionController:
Expone un endpoint REST que recibe una dirección (desde el formulario de creación de noticia). Este controller es responsable de recibir la solicitud desde la vista y delegar la normalización de la dirección al service correspondiente.

#### DireccionService:
Contiene la lógica para conectarse con el servicio externo USIG. Utiliza un cliente HTTP (RestTemplate de Spring) para enviar la dirección a la API de USIG y procesar la respuesta, extrayendo la dirección normalizada y las coordenadas geográficas.

#### Flujo típico:
1. El administrador ingresa una dirección en el formulario de noticia.
2. El DireccionController recibe la dirección desde la vista y llama al DireccionService.
3. El DireccionService consulta el servicio USIG, recibe la dirección normalizada y las coordenadas.
4. El resultado se almacena junto con la noticia y se utiliza para mostrar la ubicación en el mapa.

## Normalización de direcciones:

### Cómo se construye la petición en DireccionService
En el método normalizarDireccion de DireccionService, se construye una URL con los parámetros requeridos por USIG:
```java
// Fragmento relevante de DireccionService.java
String url = UriComponentsBuilder.fromUriString("http://servicios.usig.buenosaires.gob.ar/normalizar")
    .queryParam("direccion", direccion)      // Dirección a normalizar (ej: "Av. Corrientes 1234, CABA")
    .queryParam("geocodificar", "true")      // Solicita que devuelva coordenadas
    .queryParam("maxOptions", "5")           // Limita la cantidad de resultados
    .build()
    .toUriString();

// Realizar la petición al servicio
String response = restTemplate.getForObject(url, String.class);
```
### Ejemplo de petición directa al servicio USIG.
Puedes probar el servicio USIG directamente desde tu navegador:
```http
    http://servicios.usig.buenosaires.gob.ar/normalizar?direccion=Av.%20Corrientes%201234,%20CABA&geocodificar=true&maxOptions=5
```
### Ejemplo de respuesta del servicio USIG
```json
{
  "direccionesNormalizadas": [
    {
      "altura": 1234,
      "cod_calle": 3174,
      "cod_calle_cruce": null,
      "cod_partido": "caba",
      "coordenadas": {
        "srid": 4326,
        "x": "-58.384222",
        "y": "-34.603939"
      },
      "direccion": "CORRIENTES AV. 1234, CABA",
      "nombre_calle": "CORRIENTES AV.",
      "nombre_calle_cruce": "",
      "nombre_localidad": "CABA",
      "nombre_partido": "CABA",
      "tipo": "calle_altura"
    }
  ]
}
```

### Procesamiento de la respuesta de USIG y construcción de la ubicación
Luego de realizar la petición HTTP al servicio USIG, la respuesta (en formato JSON) es procesada en el método privado procesarRespuesta de DireccionService. Este método convierte el JSON en un objeto Java (Ubicacion) que almacena la dirección normalizada y las coordenadas geográficas.
#### Fragmento relevante de procesarRespuesta:
```java
private Optional<Ubicacion> procesarRespuesta(String respuestaJson) {
    try {
        JsonNode rootNode = objectMapper.readTree(respuestaJson);
        JsonNode primeraDireccion = rootNode.get("direccionesNormalizadas").get(0);

        // Extraer datos relevantes
        String direccionNormalizada = primeraDireccion.get("direccion").asText();
        double latitud = primeraDireccion.get("coordenadas").get("y").asDouble();
        double longitud = primeraDireccion.get("coordenadas").get("x").asDouble();

        return Optional.of(Ubicacion.builder()
                .direccion(direccionNormalizada)
                .latitud(latitud)
                .longitud(longitud)
                .build());
    } catch (Exception e) {
        // Manejo de errores
        return Optional.empty();
    }
}
```
De esta forma, cada vez que se normaliza una dirección, se obtiene un objeto Ubicacion con los datos listos para ser almacenados junto a la noticia y utilizados en la vista.

### Uso de las coordenadas con Leaflet.js
Con las coordenadas (latitud y longitud) obtenidas desde USIG, puedes mostrar la ubicación de la noticia en un mapa interactivo usando Leaflet.js en la vista de detalle. Por ejemplo:
```javascript
// En tu plantilla Thymeleaf (noticias/ver.html)
<div id="map-data"
     th:data-lat="${noticia.ubicacion.latitud}"
     th:data-lng="${noticia.ubicacion.longitud}"
     th:data-address="${noticia.ubicacion.direccion}">
</div>
<div id="map" style="height: 300px;"></div>

<script src="https://unpkg.com/leaflet@1.9.4/dist/leaflet.js"></script>
<script>
    const mapData = document.getElementById('map-data');
    if (mapData) {
        const lat = parseFloat(mapData.getAttribute('data-lat'));
        const lng = parseFloat(mapData.getAttribute('data-lng'));
        // ...verificar que lat y lng sean válidos...
        if (!isNaN(lat) && !isNaN(lng)) {
            // Inicializar el mapa centrado en la ubicación
            const map = L.map('map').setView([lat, lng], 15);
            L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png').addTo(map);
            L.marker([lat, lng]).addTo(map)
                .bindPopup(mapData.getAttribute('data-address')).openPopup();
        }
    }
</script>
```

## Seguridad y autenticación
El acceso a la administración de noticias está protegido mediante autenticación basada en Spring Security. Solo los usuarios autenticados con rol de administrador pueden crear o eliminar noticias.

### Implementación propia de autenticación
- Se implementa la interfaz UserDetailsService mediante la clase CuentaService, que se encarga de cargar los usuarios desde la base de datos (MongoDB) y proveer los detalles necesarios para la autenticación.
- El sistema utiliza roles para distinguir entre administradores y vecinos.
- Los detalles de usuario (UserDetails) se construyen a partir de la entidad Cuenta y se asignan los roles correspondientes.

