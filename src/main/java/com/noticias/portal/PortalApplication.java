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

            // Definición de noticias de prueba
            Noticia[] noticiasDemo = {
            Noticia.builder()
                .id("1")
                .titulo("Avances en inteligencia artificial")
                .descripcion("El futuro de la tecnología.")
                .cuerpo("Grandes empresas tecnológicas están invirtiendo en nuevos modelos de inteligencia artificial generativa.")
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
                .descripcion("El dólar alcanza un nuevo récord.")
                .cuerpo("La inflación global y conflictos geopolíticos están afectando seriamente a las economías emergentes.")
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
                .descripcion("Participación récord de corredores.")
                .cuerpo("Más de 500 personas participaron del evento deportivo anual organizado por el municipio.")
                .fechaPublicacion(LocalDateTime.now().minusDays(3))
                .tema(Tema.DEPORTES)
                .fotos(List.of(
                    Foto.builder()
                        .url("https://images.unsplash.com/photo-1596727362302-b8d891c42ab8?q=80&w=1985&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D")
                        .descripcion("Maratón 2024")
                        .build()
                ))
                .build(),

            Noticia.builder()
                .id("4")
                .titulo("Nueva muestra de arte local")
                .descripcion("La cultura florece en el centro comunitario.")
                .cuerpo("Artistas locales exponen sus obras durante todo el mes con entrada libre y gratuita.")
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
                .descripcion("Vacunas disponibles en centros de salud.")
                .cuerpo("El Ministerio de Salud lanza una nueva campaña con énfasis en adultos mayores y niños.")
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
