# Portal de Noticias (Demo)

Este proyecto es una pequeña demo que implementa las funcionalidades mínimas requeridas para un portal de noticias:

- Permitir la creación de noticias por parte de un administrador autenticado.
- Permitir la consulta y visualización de noticias por parte de los visitantes del portal.
- Integrar la interacción con sistemas externos de normalización de direcciones y visualización geográfica en mapas.

Características principales:
- Visualización de noticias con filtros por tema y búsqueda por título.
- Vista en detalle de cada noticia, incluyendo mapa interactivo si la noticia tiene ubicación.
- Administración de noticias (crear/eliminar) mediante autenticación.
- Normalización de direcciones usando USIG - Normalizador de Direcciones v2.1.2.
- Visualización de ubicaciones en mapas usando Leaflet.js.
- Persistencia de datos en MongoDB.

## Tecnologías utilizadas:
- Backend: Spring Boot (Java), Maven Wrapper (mvnw)
- Frontend: Thymeleaf, Leaflet.js
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
1. Visualización de mapas con Leaflet.js
Cuando una noticia tiene una ubicación asociada, en la vista de detalle se muestra un mapa interactivo usando Leaflet.js. El mapa marca la posición de la noticia y permite al usuario visualizar el entorno.
- El código de integración se encuentra en la plantilla correspondiente (por ejemplo, noticias/ver.html).
- Leaflet se carga desde CDN y utiliza las coordenadas almacenadas en la base de datos.

2. Normalización de direcciones con USIG
- Patrón de diseño: Controller-Service
Para desacoplar la lógica de negocio de la lógica de presentación y facilitar la integración con servicios externos, se utiliza el patrón Controller-Service:

- DireccionController:
Expone un endpoint REST que recibe una dirección (por ejemplo, desde el formulario de creación de noticia). Este controller es responsable de recibir la solicitud del frontend y delegar la normalización de la dirección al service correspondiente.
- DireccionService:
Contiene la lógica para conectarse con el servicio externo USIG. Utiliza un cliente HTTP (por ejemplo, RestTemplate de Spring) para enviar la dirección a la API de USIG y procesar la respuesta, extrayendo la dirección normalizada y las coordenadas geográficas.
- Flujo típico:
1. El administrador ingresa una dirección en el formulario de noticia.
2. El frontend envía la dirección al endpoint del DireccionController.
3. El DireccionController llama al DireccionService.
4. El DireccionService consulta el servicio USIG, recibe la dirección normalizada y las coordenadas.
5. El resultado se almacena junto con la noticia y se utiliza para mostrar la ubicación en el mapa.

# Ejemplo de endpoint para normalización:
```http
POST /api/direccion/normalizar
Content-Type: application/json

{
  "direccion": "Av. Corrientes 1234, CABA"
}
```

# Ejemplo de respuesta:
```json
{
  "direccionNormalizada": "Av. Corrientes 1234, Ciudad Autónoma de Buenos Aires",
  "lat": -34.6037,
  "lng": -58.3816
}
```

# Seguridad y autenticación
El acceso a la administración de noticias está protegido mediante autenticación basada en Spring Security. Solo los usuarios autenticados con rol de administrador pueden crear o eliminar noticias.

# Implementación propia de autenticación
- Se implementa la interfaz UserDetailsService mediante la clase CuentaService, que se encarga de cargar los usuarios desde la base de datos (MongoDB) y proveer los detalles necesarios para la autenticación.
- El sistema utiliza roles para distinguir entre administradores y vecinos.
- Los detalles de usuario (UserDetails) se construyen a partir de la entidad Cuenta y se asignan los roles correspondientes.

# Estructura del proyecto

portal/
├── src/
│   ├── main/
│   │   ├── java/com/noticias/portal/         # Código Java (Spring Boot)
│   │   │   ├── controller/                   # Incluye DireccionController, NoticiasController
│   │   │   ├── service/                      # Incluye DireccionService, CuentaService, NoticiaService
│   │   │   ├── repositories/                 # Incluye CuentaRepository, NoticiaRepository
│   │   │   ├── models/                       # Incluye Cuenta, Noticia
│   │   ├── resources/templates/noticias/     # Vistas Thymeleaf
│   │   └── resources/static/                 # Recursos estáticos (JS, CSS)
├── mvnw, mvnw.cmd                            # Maven Wrapper
├── pom.xml                                   # Dependencias Maven
├── README.md                                 # Documentación del proyecto
├── ejemplos.txt                              # Ejemplos de creacion de noticia
