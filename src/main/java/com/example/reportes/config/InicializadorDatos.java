package com.example.reportes.config;

import java.time.LocalDateTime;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.reportes.models.Rol;
import com.example.reportes.models.Usuario;
import com.example.reportes.repositories.RolRepository;
import com.example.reportes.repositories.UsuarioRepository;

@Component
public class InicializadorDatos implements CommandLineRunner {

    private final RolRepository rolRepository;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public InicializadorDatos(RolRepository rolRepository,
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder) {
        this.rolRepository = rolRepository;
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if (rolRepository.count() == 0) {
            // Rol ADMINISTRADOR con permisos completos
            Rol admin = Rol.builder()
                    .nombre("ADMINISTRADOR")
                    .permisos("{\"puede_editar\":true, \"puede_eliminar\":true, \"modulos\":[\"*\"]}")
                    .build();

            // Rol USUARIO con permisos básicos
            Rol usuario = Rol.builder()
                    .nombre("USUARIO")
                    .permisos("{\"puede_editar\":false, \"puede_eliminar\":false, \"modulos\":[\"documentos\"]}")
                    .build();

            rolRepository.saveAll(Set.of(admin, usuario));
        }

        // Crear usuario admin si no existe
        if (usuarioRepository.findByRol_Id(1).isEmpty()) {
            Rol rolAdmin = rolRepository.findByNombre("ADMINISTRADOR")
            .orElseThrow(() -> new IllegalStateException("Rol ADMINISTRADOR no encontrado"));

            Usuario admin = Usuario.builder()
                    .username("admin")
                    .email("admin@tudominio.com")
                    .password(passwordEncoder.encode("admin")) // Cambia esta contraseña
                    .rol(rolAdmin)
                    .nombreCompleto("Administrador")
                    .activo(true)
                    .fechaCreacion(LocalDateTime.now())
                    .build();

            usuarioRepository.save(admin);
        }
    }

}