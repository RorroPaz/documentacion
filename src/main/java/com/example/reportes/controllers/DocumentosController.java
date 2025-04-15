package com.example.reportes.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model; // ¡Este es el import correcto!
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.reportes.models.Usuario;
import com.example.reportes.repositories.UsuarioRepository;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

@Controller
@RequestMapping("/documentos")
public class DocumentosController {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/dashboard")
    public String dashboard(Model model, HttpServletRequest request) { // Ahora sí reconocerá addAttribute()

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        model.addAttribute("usuario", usuario);

        // Datos de ejemplo para crear el grafico
        Map<String, Integer> stats = new HashMap<>();
        stats.put("totalDocumentos", 124);
        stats.put("documentosPendientes", 12);
        stats.put("documentosAprobados", 98);
        model.addAttribute("stats", stats);
        return "documentos/dashboard"; // template
    }
}

/*
 * Authentication:
 * Contiene todos los datos en .getPrincipal
 * 
 * 
 * OPCION 1:
 * //Obtener el authentication object
 * Authentication authentication =
 * SecurityContextHolder.getContext().getAuthentication();
 * 
 * //Obtener el nombre de usuario
 * String username = authentication.getName();
 * model.addAttribute("username", username);
 * 
 * Si necesitas más datos del usuario (tu entidad Usuario completa):
 * if (authentication.getPrincipal() instanceof UserDetails) {
 * UserDetails userDetails = (UserDetails) authentication.getPrincipal();
 * // Aquí puedes obtener más datos según tu implementación
 * }
 * 
 * La OPCION 2 es la que esta implementada en el proyecto
 */
