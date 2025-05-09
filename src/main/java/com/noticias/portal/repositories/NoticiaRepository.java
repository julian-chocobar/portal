package com.noticias.portal.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.noticias.portal.enums.Tema;
import com.noticias.portal.models.Noticia;

public interface NoticiaRepository extends MongoRepository<Noticia, String> {

    // Filtrar por titulo y tema
    Page<Noticia> findByTituloContainingIgnoreCaseAndTema(String titulo, Tema tema, Pageable pageable);

    // Solo por titulo
    Page<Noticia> findByTituloContainingIgnoreCase(String titulo, Pageable pageable);

    // Solo por tema
    Page<Noticia> findByTema(Tema tema, Pageable pageable);
}
