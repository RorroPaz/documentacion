package com.example.reportes.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.reportes.models.Usuario;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    // Se ocupa el Optional por si el usuario no existe y se regresa un valor null
    // Optional maneja valores que pueden ser null
    Optional<Usuario> findByUsername(String username); // sigue existiendo el findByUsername normal , que te regresa un objeto <User>

    // Otro método de tipo opcional
    Optional<Usuario> findByEmail(String email);

    // existe el usuario ?
    boolean existsByUsername(String username);

    // existe el usuario ?
    boolean existsByEmail(String email);

} 