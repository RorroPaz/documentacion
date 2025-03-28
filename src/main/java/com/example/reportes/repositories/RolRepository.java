package com.example.reportes.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.reportes.models.Rol;
import java.util.Optional;

public interface RolRepository extends JpaRepository<Rol, Integer> {
    Optional<Rol> findByNombre(String nombre); // Para buscar por nombre (ej: "USUARIO")
}