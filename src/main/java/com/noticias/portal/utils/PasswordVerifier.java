// package com.noticias.portal.utils;

// import org.springframework.boot.CommandLineRunner;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.crypto.password.PasswordEncoder;

// import lombok.extern.slf4j.Slf4j;

// @Configuration
// @Slf4j
// public class PasswordVerifier {

//     @Bean
//     public CommandLineRunner verifyPassword(PasswordEncoder passwordEncoder) {
//         return args -> {
//             String storedHash = "$2a$10$rTr60sf0ChT2TMID2ggPeO0U6VgMQwmcbCH3Fu.GAfC7RapyYYO56";
//             String rawPassword = "admin123";
            
//             boolean matches = passwordEncoder.matches(rawPassword, storedHash);
            
//             log.info("Verificación de contraseña para 'admin123':");
//             log.info("Hash almacenado: {}", storedHash);
//             log.info("¿La contraseña coincide con el hash?: {}", matches);
            
//             // Generar un nuevo hash para la misma contraseña
//             String newHash = passwordEncoder.encode(rawPassword);
//             log.info("Nuevo hash generado: {}", newHash);
//             log.info("¿El nuevo hash coincide con la contraseña?: {}", passwordEncoder.matches(rawPassword, newHash));
//         };
//     }
// } 