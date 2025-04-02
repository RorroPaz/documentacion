package com.example.reportes.services;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.reportes.models.Usuario;

import com.example.reportes.repositories.UsuarioRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private static final Logger log = LoggerFactory.getLogger(CustomUserDetailsService.class);

    private final UsuarioRepository usuarioRepository;

    public CustomUserDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException { // Retorna un UserDetails
        // 1. Busca el usuario en la BD
        Usuario usuario = usuarioRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        log.info("Usuario encontrado. ID: {}, Rol: {}, Contraseña: {}", 
        usuario.getId(), usuario.getRol().getNombre(),usuario.getPassword());

        return User.builder()
                .username(usuario.getUsername())
                .password(usuario.getPassword())
                .roles(usuario.getRol().getNombre()) 
                .build();
    }
}

/*
 * Recibe el username del formulario de login.
 * Busca el usuario en la BD usando UsuarioRepository.
 * Si no existe, lanza UsernameNotFoundException.
 * Si existe, construye un objeto UserDetails (que Spring Security usa para verificar credenciales y permisos).
 *  
 * ¿Cómo y cuándo se llama a este servicio?
 * 
 * Automáticamente por Spring Security cuando:
 * Se envía el formulario de login (loginProcessingUrl).
 * Se hace una petición HTTP autenticada.
 * 
 * Flujo detallado:
 * El usuario ingresa credenciales en /login.
 * Spring Security llama a loadUserByUsername(username).
 * Compara el password (usando BCryptPasswordEncoder).
 * Si todo coincide, crea una sesión autenticada.
 */

 /*
  *
    FORMAS DE OBTENER UN USARIO PARA VALIDAR QUE EXISTE
    OPC 1 con mas pasos:
    Nota: Esta opcion hace uso del metodo personalizado Optional<User> del repositorio, tienes que crearlo
    Optional<Usuario> usuarioOpt = usuarioRepository.findByUsername(username);
    if (usuarioOpt.isEmpty()) {
        log.error("Usuario no encontrado en la BD");
        throw new UsernameNotFoundException("Usuario no encontrado");
    }
    Usuario usuario = usuarioOpt.get();

    OPC 2:
    Usuario usuario = usuarioRepository.findByUsername(username)
    .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
  */