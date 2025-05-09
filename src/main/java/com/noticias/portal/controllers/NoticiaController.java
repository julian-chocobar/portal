package com.noticias.portal.controllers;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.noticias.portal.dtos.NoticiaDTO;
import com.noticias.portal.enums.Tema;
import com.noticias.portal.services.NoticiaService;
import org.springframework.ui.Model;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/noticias")
@RequiredArgsConstructor
public class NoticiaController {

    private final NoticiaService noticiaService;

    @GetMapping
    public String listarNoticias(
        @RequestParam(required = false) String titulo,
        @RequestParam(required = false) Tema tema,
        @PageableDefault(size = 10, sort = "fechaPublicacion", direction = Sort.Direction.DESC) Pageable pageable,
        Model model
    ) {
        Page<NoticiaDTO> pagina = noticiaService.buscarFiltrado(titulo, tema, pageable);
        model.addAttribute("pagina", pagina);
        model.addAttribute("titulo", titulo);
        model.addAttribute("tema", tema);
        model.addAttribute("temas", Tema.values());
        return "noticias/lista";
    }

    @GetMapping("/ver/{id}")
    public String verNoticia(@PathVariable String id, Model model) {
        NoticiaDTO noticia = noticiaService.buscarPorId(id);
        model.addAttribute("noticia", noticia);
        return "noticias/ver";
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @GetMapping("/crear")
    public String mostrarFormulario(Model model) {
        model.addAttribute("noticia", new NoticiaDTO());
        model.addAttribute("temas", Tema.values());
        return "noticias/crear";
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PostMapping("/guardar")
    public String guardarNoticia(@ModelAttribute("noticia") NoticiaDTO noticiaDTO) {
        noticiaDTO.setFechaPublicacion(LocalDateTime.now().toString());
        noticiaService.guardarDTO(noticiaDTO);
        return "redirect:/noticias";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarNoticia(@PathVariable String id) {
        noticiaService.eliminarNoticia(id);
        return "redirect:/noticias";
    }

}
