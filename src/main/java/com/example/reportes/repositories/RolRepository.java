package com.example.reportes.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.reportes.models.Rol;
import java.util.Optional;

public interface RolRepository extends JpaRepository<Rol, Integer> {
    // Este es polimorfismo del metodo findByNombre, este puede regresar nulos 
    Optional<Rol> findByNombre(String nombre); // Para buscar por nombre (ej: "USUARIO")
}