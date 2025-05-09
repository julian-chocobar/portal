// package com.noticias.portal.config;

// import org.springframework.ui.Model;
// import org.springframework.web.bind.annotation.ControllerAdvice;
// import org.springframework.web.bind.annotation.ExceptionHandler;

// @ControllerAdvice
// public class GlobalExceptionHandler {

//     @ExceptionHandler({
//        IllegalArgumentException.class,
//         IllegalStateException.class,
//         RuntimeException.class
//     })
//     public String handleRuntimeException(RuntimeException ex, Model model) {
//         model.addAttribute("message", ex.getMessage());
//         return "error/custom-error";
//     }
// }