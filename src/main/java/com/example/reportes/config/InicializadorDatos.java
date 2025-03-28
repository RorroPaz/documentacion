package com.example.reportes.config;

import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.reportes.models.Rol;
import com.example.reportes.repositories.RolRepository;

@Component
public class InicializadorDatos implements CommandLineRunner {

    private final RolRepository rolRepository;

    public InicializadorDatos(RolRepository rolRepository) {
        this.rolRepository = rolRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (rolRepository.count() == 0) {
            // Rol ADMINISTRADOR con permisos completos
            Rol admin = Rol.builder()
                .nombre("ADMINISTRADOR")
                .permisos("{\"puede_editar\":true, \"puede_eliminar\":true, \"modulos\":[\"*\"]]}")
                .build();

            // Rol USUARIO con permisos básicos
            Rol usuario = Rol.builder()
                .nombre("USUARIO")
                .permisos("{\"puede_editar\":false, \"puede_eliminar\":false, \"modulos\":[\"documentos\"]]}")
                .build();

            rolRepository.saveAll(Set.of(admin, usuario));
        }
    }
}