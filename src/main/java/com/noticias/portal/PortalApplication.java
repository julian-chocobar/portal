package com.noticias.portal;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.noticias.portal.enums.Rol;
import com.noticias.portal.enums.Tema;
import com.noticias.portal.models.Cuenta;
import com.noticias.portal.models.Foto;
import com.noticias.portal.models.Noticia;
import com.noticias.portal.models.Ubicacion;
import com.noticias.portal.repositories.CuentaRepository;
import com.noticias.portal.repositories.NoticiaRepository;


@SpringBootApplication
public class PortalApplication {

	public static void main(String[] args) {
		SpringApplication.run(PortalApplication.class, args);
	}

	@Bean
	public CommandLineRunner initAdmin(CuentaRepository cuentaRepository, PasswordEncoder passwordEncoder, NoticiaRepository noticiaRepository) {
		return args -> {
			String emailAdmin = "admin@portal.com";

			if (cuentaRepository.existsByEmail(emailAdmin)) {
				System.out.println("ℹ️ El usuario admin ya existe.");
				return;
			}else{
                Cuenta admin = new Cuenta();
                admin.setEmail(emailAdmin);
                admin.setClave(passwordEncoder.encode("admin123")); 
                admin.setRol(Rol.ADMINISTRADOR);

                cuentaRepository.save(admin);
                System.out.println("✅ Usuario administrador creado: " + emailAdmin);
            }

			// Crear o verificar noticias de prueba individualmente
            System.out.println("ℹ️ Verificando noticias de prueba...");
            int noticiasCreadas = 0;
            String descripcionIa = "Un equipo de investigadores ha desarrollado un sistema de IA capaz de resolver problemas complejos con un rendimiento similar al de expertos humanos, marcando un hito en el campo de la inteligencia artificial.";    
            String cuerpoIa = "En un avance que podría redefinir el futuro de la tecnología, científicos de la empresa DeepMind (parte de Google) han presentado un nuevo modelo de inteligencia artificial capaz de superar pruebas de razonamiento lógico y resolución de problemas a niveles comparables con los de profesionales humanos.<br><br>"
    + "El sistema, llamado ReasoningNet, utiliza una arquitectura innovadora que combina redes neuronales avanzadas con algoritmos de toma de decisiones, logrando un 92% de precisión en evaluaciones estándar de razonamiento abstracto. Estas pruebas incluyen resolución de acertijos matemáticos, interpretación de contextos ambiguos y planificación estratégica, áreas en las que hasta ahora las IA tenían limitaciones.<br><br>"
    + "<b>\"Este es un paso crucial hacia máquinas que puedan pensar de manera más similar a los humanos\"</b>, explicó la Dra. Elena Torres, líder del proyecto. \"No se trata solo de procesar datos, sino de entender relaciones complejas y tomar decisiones con información incompleta\".<br><br>"
    + "El avance podría tener aplicaciones en campos como la medicina, donde la IA podría ayudar en diagnósticos más precisos, o en la industria, optimizando procesos logísticos con mayor autonomía. Sin embargo, también ha generado debates éticos sobre los límites del desarrollo de la inteligencia artificial y su impacto en el empleo.<br><br>"
    + "Mientras tanto, otras empresas como OpenAI y Meta han anunciado que trabajan en tecnologías similares, lo que sugiere que la competencia por crear la IA más avanzada está entrando en una nueva fase. Expertos predicen que, en los próximos años, estos sistemas podrían integrarse en herramientas cotidianas, cambiando radicalmente la forma en que interactuamos con la tecnología.<br><br>"
    + "<b>El próximo desafío</b>, según los investigadores, será mejorar la capacidad de aprendizaje autónomo de estos modelos, acercándolos aún más a la versatilidad del pensamiento humano.";


            String descripcionFinanzas = "Los mercados bursátiles registran fuertes caídas tras el aumento de tasas de interés y señales de desaceleración económica, generando incertidumbre entre inversionistas.";
            String cuerpoFinanzas = "Una ola de pesimismo recorre los mercados financieros globales luego de que los principales índices bursátiles sufrieran sus peores pérdidas en meses, impulsados por el temor a una recesión económica y el endurecimiento de las políticas monetarias de los bancos centrales.<br><br>"
    + "El Dow Jones cayó un 3.1%, el S&P 500 un 3.8% y el Nasdaq se desplomó 4.5%, afectado especialmente por la baja en acciones tecnológicas. En Europa, el IBEX 35 (España) retrocedió un 2.7%, mientras que el DAX (Alemania) y el CAC 40 (Francia) perdieron más del 3%. En América Latina, las bolsas de Brasil y México también cerraron en rojo.<br><br>"
    + "<b>Causas de la crisis:</b><br>"
    + "<b>Subida agresiva de tasas:</b> La Reserva Federal (Fed) elevó los tipos de interés en 0.75%, la mayor alza en casi 30 años, para combatir la inflación.<br>"
    + "<b>Advertencias de recesión:</b> El Banco Mundial y el FMI recortaron sus previsiones de crecimiento global, señalando riesgos por la guerra en Ucrania y la crisis energética.<br>"
    + "<b>Fuga hacia activos seguros:</b> Los bonos del Tesoro estadounidense y el dólar se fortalecieron, mientras que las criptomonedas, como Bitcoin, cayeron más del 10%.<br><br>"
    + "<b>Reacciones:</b><br>"
    + "<b>Inversionistas en alerta:</b> \"El mercado está pricing una contracción económica; la volatilidad seguirá\", advirtió un analista de JPMorgan.<br>"
    + "<b>Gobiernos bajo presión:</b> El presidente de EE.UU., Joe Biden, aseguró que \"la economía es resiliente\", pero admitió \"tiempos difíciles\".<br>"
    + "<b>BCE y Fed en la mira:</b> Los bancos centrales enfrentan el dilema de controlar la inflación sin ahogar el crecimiento.<br><br>"
    + "¿Qué viene? Los expertos anticipan más turbulencias en las próximas semanas, con atención puesta en los informes de empleo y resultados empresariales. Mientras tanto, los pequeños ahorradores son los más expuestos a esta tormenta financiera.";
           
            String descripcionMaratón = "Con gran participación familiar y ambiente festivo, se realizó la 5ta edición de la maratón barrial, promoviendo vida saludable y la unión del vecindario.";
            String cuerpoMaratón = "El Parque Central fue escenario este domingo de una vibrante Maratón Vecinal que reunió a más de 500 participantes de todas las edades en un evento que combinó deporte, salud y espíritu comunitario.<br><br>"
    + "La competencia, organizada por la Asociación de Vecinos Activos, ofreció tres categorías:<br>"
    + "3K familiar (con disfraces y mascotas)<br>"
    + "5K competitiva<br>"
    + "1K infantil (para niños de 5 a 12 años)<br><br>"
    + "<b>Lo más destacado:</b><br>"
    + "✔ El ganador absoluto fue Martín Rojas (24), vecino del barrio, con un tiempo de 17:32 en 5K<br>"
    + "✔ Rocío Méndez (9) emocionó al cruzar la meta en silla de ruedas con ayuda de sus compañeros del colegio<br>"
    + "✔ Stands de nutrición y primeros auxilios brindaron controles gratuitos<br>"
    + "✔ La banda local 'Los de la Esquina' animó la llegada con música en vivo<br><br>"
    + "<b>Testimonios:</b><br>"
    + "\"No se trataba de competir sino de compartir. Vi abuelos, padres con coches de bebé y hasta perros corriendo juntos\", comentó Laura Torres, una de las organizadoras.<br><br>"
    + "El evento cerró con una feria saludable donde emprendedores locales ofrecieron frutas, jugos naturales y artesanías. Los fondos recaudados ($1.2 millones) serán destinados a renovar el gimnasio al aire libre del parque.<br><br>"
    + "<b>Próximo desafío:</b> Los organizadores anunciaron que para octubre planean una 'Carrera Nocturna con Luces LED', buscando repetir el éxito de esta jornada que ya se consolida como tradición barrial.";
           
            String descripcionArte = "La cultura florece en el centro comunitario. 15 artistas emergentes exponen obras que fusionan técnicas tradicionales con innovación digital en una exposición gratuita que promete ser el evento cultural de la temporada.";
            String cuerpoArte = "El Centro Cultural Municipal inauguró anoche 'Raíces Contemporáneas', una impactante muestra que reúne el trabajo de 15 talentos locales seleccionados por un jurado internacional. La exposición, que estará abierta hasta el 15 de noviembre, ya ha sido catalogada como 'un diálogo entre la herencia cultural y las nuevas expresiones artísticas' por la crítica especializada.<br><br>"
    + "<b>Lo más destacado de la muestra:</b><br>"
    + "🎨 <b>Instalación 'Memoria Viva' de Valeria Sosa:</b><br>"
    + "Combina bordado tradicional con proyecciones holográficas<br>"
    + "Representa historias de inmigrantes de la región<br><br>"
    + "🖌️ <b>Colección 'Pixeles Ancestrales' del colectivo Arte Digital:</b><br>"
    + "Retratos de ancianos de la comunidad convertidos en NFT interactivos<br>"
    + "Los visitantes pueden escanear códigos QR para escuchar sus relatos<br><br>"
    + "🖼️ <b>Mural colaborativo en vivo:</b><br>"
    + "Los asistentes pueden agregar su aporte durante todo el mes<br>"
    + "Será donado al hospital pediátrico<br><br>"
    + "<b>Datos clave:</b><br>"
    + "📅 <i>Hasta el 15/11</i> | 🕒 Martes a domingo de 15 a 21 hs<br>"
    + "📍 Sala Principal del Centro Cultural (Av. Libertad 123)<br>"
    + "🎟️ Entrada libre con reserva previa en cultura.municipio.com<br><br>"
    + "<b>Declaraciones:</b><br>"
    + "'Esta exposición demuestra que nuestra ciudad es un semillero de artistas que reinventan nuestra identidad', destacó el Secretario de Cultura, Luciano Morales, durante la apertura que contó con más de 300 asistentes.<br><br>"
    + "La muestra incluye talleres gratuitos los fines de semana:<br>"
    + "Técnicas mixtas para jóvenes (sábados 16 hs)<br>"
    + "Arte digital para adultos mayores (domingos 10 hs)<br><br>"
    + "<b>Próximamente:</b> Los tres artistas más destacados viajarán a representar la provincia en la Bienal Internacional de Arte Emergente en Barcelona.";
           
            String descripcionVacunación = "El Ministerio de Salud local anunció la disponibilidad de dosis contra influenza, COVID-19 y neumococo en todos los centros públicos con horarios extendidos hasta agosto.";
            String cuerpoVacunación = "A partir de este lunes 10 de junio, todos los centros de salud municipales y hospitales públicos de la ciudad contarán con stock garantizado de vacunas para la campaña de invierno, según confirmó la Secretaría de Salud en conferencia de prensa.<br><br>"
    + "<b>Vacunas disponibles:</b><br>"
    + "Influenza estacional (para grupos de riesgo)<br>"
    + "Refuerzos COVID-19 (para mayores de 12 años)<br>"
    + "Antineumocócica (adultos mayores y pacientes crónicos)<br>"
    + "Calendario regular (para niños y adolescentes)<br><br>"
    + "<b>Novedades:</b><br>"
    + "✅ Horarios ampliados: Atención de 8 a 20 hs en 15 centros estratégicos<br>"
    + "✅ Puestos móviles: Vacunación en plazas y estaciones de tren (consultar cronograma)<br>"
    + "✅ Sin turno previo: Solo presentar DNI y carnet de vacunación<br><br>"
    + "<b>Grupos prioritarios:</b><br>"
    + "Mayores de 65 años<br>"
    + "Embarazadas<br>"
    + "Personal de salud<br>"
    + "Personas con enfermedades crónicas (presentar certificado médico)<br><br>"
    + "'Queremos alcanzar el 80% de cobertura antes del pico invernal', explicó la Dra. Laura Mendez, directora de Epidemiología, quien recordó que las dosis son gratuitas en todos los centros públicos.<br><br>"
    + "<b>Recomendaciones:</b><br>"
    + "Llevar carnet de vacunación<br>"
    + "Usar barbijo en espacios cerrados<br>"
    + "No concurrir con síntomas febriles<br><br>"
    + "<b>Centros con mayor capacidad:</b><br>"
    + "Hospital Central (Av. San Martín 2500)<br>"
    + "Centro de Salud N°3 (Belgrano 720)<br>"
    + "Unidad Sanitaria Noroeste (Ruta 8 km 12)<br><br>"
    + "Para consultar el centro más cercano con disponibilidad, los vecinos pueden ingresar a www.saludmunicipio.gob.ar/vacunacion o llamar al 147.";
           
            // Definición de noticias de prueba
            Noticia[] noticiasDemo = {
            Noticia.builder()
                .id("1")
                .titulo("Avances en inteligencia artificial")
                .descripcion(descripcionIa)
                .cuerpo(cuerpoIa)
                .fechaPublicacion(LocalDateTime.now().minusDays(1))
                .tema(Tema.TECNOLOGIA)
                .fotos(List.of(
                    Foto.builder()
                        .url("https://images.unsplash.com/photo-1697577418970-95d99b5a55cf?q=80&w=1992&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D")
                        .descripcion("IA generativa")
                        .build()
                ))
                .build(),

            Noticia.builder()
                .id("2")
                .titulo("Crisis en el mercado financiero")
                .descripcion(descripcionFinanzas)
                .cuerpo(cuerpoFinanzas)
                .fechaPublicacion(LocalDateTime.now().minusDays(2))
                .tema(Tema.ECONOMIA)
                .fotos(List.of(
                    Foto.builder()
                        .url("https://images.unsplash.com/photo-1621629057099-c7cf1fb8ca1e?q=80&w=2070&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D")
                        .descripcion("Mercado financiero")
                        .build()
                ))
                .build(),

            Noticia.builder()
                .id("3")
                .titulo("Maratón vecinal en el parque")
                .descripcion(descripcionMaratón)
                .cuerpo(cuerpoMaratón)
                .fechaPublicacion(LocalDateTime.now().minusDays(3))
                .tema(Tema.DEPORTES)
                .fotos(List.of(
                    Foto.builder()
                        .url("https://images.unsplash.com/photo-1596727362302-b8d891c42ab8?q=80&w=1985&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D")
                        .descripcion("Maratón 2024")
                        .build()
                ))
                .ubicacion(Ubicacion.builder().direccion(" Av. Infanta Isabel 110, caba").latitud(-34.57333333).longitud(-58.41472222).build())
                .build(),
                

            Noticia.builder()
                .id("4")
                .titulo("Nueva muestra de arte local")
                .descripcion(descripcionArte)
                .cuerpo(cuerpoArte)
                .fechaPublicacion(LocalDateTime.now().minusDays(4))
                .tema(Tema.CULTURA)
                .fotos(List.of(
                    Foto.builder()
                        .url("https://images.unsplash.com/photo-1743119638006-a01d4625745d?q=80&w=2070&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D")
                        .descripcion("Obras de artistas locales")
                        .build()
                ))
                .build(),

            Noticia.builder()
                .id("5")
                .titulo("Campaña de vacunación gratuita")
                .descripcion(descripcionVacunación)
                .cuerpo(cuerpoVacunación)
                .fechaPublicacion(LocalDateTime.now().minusDays(5))
                .tema(Tema.SALUD)
                .fotos(List.of(
                    Foto.builder()
                        .url("https://images.unsplash.com/photo-1578307985320-34b61a66c195?q=80&w=2078&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D")
                        .descripcion("Centro de vacunación")
                        .build()
                ))
                .build()
        };
        
        // Verificar y crear cada noticia individualmente
        for (Noticia noticia : noticiasDemo) {
            if (!noticiaRepository.existsById(noticia.getId())) {
                noticiaRepository.save(noticia);
                noticiasCreadas++;
                System.out.println("✅ Noticia creada: " + noticia.getTitulo());
            } else {
                System.out.println("ℹ️ Noticia ya existe: " + noticia.getTitulo());
            }
        }
        
        System.out.println("✅ Proceso completado: " + noticiasCreadas + " noticias nuevas creadas.");


		};
	}

 
}
