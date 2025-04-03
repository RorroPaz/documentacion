package com.example.reportes.controllers;

import java.time.LocalDateTime;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.reportes.models.Rol;
import com.example.reportes.models.Usuario;
import com.example.reportes.repositories.RolRepository;
import com.example.reportes.repositories.UsuarioRepository;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class AuthController {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UsuarioRepository usuarioRepository, RolRepository rolRepository,
            PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Muestra formulario de login
    @GetMapping("/login")
    public String mostrarLogin(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout,
            @RequestParam(value = "registered", required = false) String registered,
            Model model,
            HttpServletRequest request) {

        // Limpia cualquier autenticación existente
        SecurityContextHolder.clearContext();

        // Los RquestParam son todos los posinles Parametros que puede cachar
        model.addAttribute("error", error != null);
        model.addAttribute("logout", logout != null);
        model.addAttribute("registered", registered != null);
        return "auth/login";
    }

    // Procesa login (manejado por Spring Security)
    // @PostMapping("/login")
    // para procesar el formulario del login y validar el usuario

    // Muestra formulario de registro
    @GetMapping("/register")
    public String mostrarFormularioRegistro() {
        return "auth/register"; // Ruta de tu plantilla Thymeleaf
    }

    // Procesa registro
    @PostMapping("/register")
    public String registrarUsuario(@RequestParam String username,
            @RequestParam String email,
            @RequestParam String password,
            RedirectAttributes redirectAttributes) {

        System.out.println("Usuario registrado: ------ " + username);

        // Validar si el usuario o email ya existen
        if (usuarioRepository.existsByUsername(username)) {
            redirectAttributes.addFlashAttribute("error", "El nombre de usuario ya está en uso.");
            return "redirect:/register";
        }
        if (usuarioRepository.existsByEmail(email)) {
            redirectAttributes.addFlashAttribute("error", "El email ya está registrado.");
            return "redirect:/register";
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
        return "redirect:/login?registered=true";
    }

    // Muestra página de acceso denegado
    @GetMapping("/acceso-denegado")
    public String accesoDenegado() {
        return "error/acceso-denegado";
    }
}