package com.noticias.portal.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.noticias.portal.dtos.NoticiaDTO;
import com.noticias.portal.enums.Tema;
import com.noticias.portal.mappers.NoticiaMapper;
import com.noticias.portal.models.Noticia;
import com.noticias.portal.repositories.NoticiaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NoticiaService {

    private final NoticiaRepository noticiaRepository;
    private final NoticiaMapper noticiaMapper;

    public List<NoticiaDTO> listarTodasDTO() {
        return noticiaRepository.findAll()
                .stream()
                .map(noticiaMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Page<NoticiaDTO> buscarFiltrado(String titulo, Tema tema, Pageable pageable) {
        Page<Noticia> resultado;

        boolean hayTitulo = titulo != null && !titulo.trim().isEmpty();
        boolean hayTema = tema != null;

        if (hayTitulo && hayTema) {
            resultado = noticiaRepository.findByTituloContainingIgnoreCaseAndTema(titulo, tema, pageable);
        } else if (hayTitulo) {
            resultado = noticiaRepository.findByTituloContainingIgnoreCase(titulo, pageable);
        } else if (hayTema) {
            resultado = noticiaRepository.findByTema(tema, pageable);
        } else {
            resultado = noticiaRepository.findAll(pageable);
        }

        return resultado.map(noticiaMapper::toDTO);
    }

    public void guardarDTO(NoticiaDTO dto) {
        noticiaRepository.save(noticiaMapper.toEntity(dto));
    }

    public NoticiaDTO buscarPorId(String id) {
        return noticiaMapper.toDTO(noticiaRepository.findById(id).orElse(null));
    }


    public void eliminarNoticia(String id) {
        noticiaRepository.deleteById(id);
    }

}
