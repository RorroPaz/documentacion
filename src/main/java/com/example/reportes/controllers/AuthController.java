package com.example.reportes.controllers;

import java.time.LocalDateTime;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.reportes.models.Rol;
import com.example.reportes.models.Usuario;
import com.example.reportes.repositories.RolRepository;
import com.example.reportes.repositories.UsuarioRepository;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UsuarioRepository usuarioRepository, RolRepository rolRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/register")
    public String mostrarFormularioRegistro() {
        return "auth/register"; // Ruta de tu plantilla Thymeleaf
    }

    @PostMapping("/register")
    public String registrarUsuario(@RequestParam String username,
                                  @RequestParam String email,
                                  @RequestParam String password,
                                  RedirectAttributes redirectAttributes) {
        
        // Validar si el usuario o email ya existen
        if (usuarioRepository.existsByUsername(username)) {
            redirectAttributes.addFlashAttribute("error", "El nombre de usuario ya está en uso.");
            return "redirect:/auth/register";
        }
        if (usuarioRepository.existsByEmail(email)) {
            redirectAttributes.addFlashAttribute("error", "El email ya está registrado.");
            return "redirect:/auth/register";
        }

        // Asignar rol "USUARIO" por defecto
        Rol rolUsuario = rolRepository.findByNombre("USUARIO")
                .orElseThrow(() -> new IllegalStateException("Rol USUARIO no encontrado en la BD."));

        // Crear y guardar el usuario
        Usuario nuevoUsuario = Usuario.builder()
                .username(username)
                .email(email)
                .password(passwordEncoder.encode(password))
                .rol(rolUsuario)
                .nombreCompleto(username) // Puedes ajustar esto luego
                .activo(true)
                .fechaCreacion(LocalDateTime.now())
                .build();

        usuarioRepository.save(nuevoUsuario);

        redirectAttributes.addFlashAttribute("success", "¡Registro exitoso! Inicia sesión.");
        return "redirect:/login";
    }
}