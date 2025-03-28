package com.example.reportes.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.reportes.models.Usuario;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    // Método clave para la autenticación 
    // Es parte dela convencion de spring , este metodo del repo, lo llama el service 
    // y debe llamarse tal cual "findByUsername"
    Optional<Usuario> findByUsername(String username);
    // Se ocupa el Optional por si el usuario no existe y se regresa un valor null
    // Optional maneja valores que pueden ser null

    // Otros métodos útiles (opcionales)
    // email
    Optional<Usuario> findByEmail(String email);

    // existe el usuario ?
    boolean existsByUsername(String username);

    // existe el usuario ?
    boolean existsByEmail(String email);

} 