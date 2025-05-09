package com.noticias.portal.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/auth")
public class AuthViewController {

    @GetMapping("/login")
    public String loginPage(@RequestParam(required = false) String error,
                           @RequestParam(required = false) String logout,
                           Model model) {
        if (error != null) {
            model.addAttribute("errorMessage", "Credenciales incorrectas. Por favor, intente nuevamente.");
        }
        
        if (logout != null) {
            model.addAttribute("logoutMessage", "Ha cerrado sesión correctamente.");
        }
        
        return "auth/login";
    }
}