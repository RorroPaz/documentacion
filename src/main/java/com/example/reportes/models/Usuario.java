package com.example.reportes.models;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity // Marca la clase como una entidad
@Table(name = "usuarios")
@Getter
@Setter
@NoArgsConstructor // Genera Constructor vacio, necesario para JPA
@AllArgsConstructor
@Builder
public class Usuario {
    @Id // Designa el campo como la llave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) //
    private Integer id;

    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Column(nullable = false, length = 255) // Para almacenar el hash BCrypt
    private String password;

    @Column(name = "nombre_completo", nullable = false, length = 100)
    private String nombreCompleto;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    // FK
    @ManyToOne(fetch = FetchType.EAGER) // Se completa la relacion 1 to Many Rol -> Usuarios
    @JoinColumn(name = "rol_id", nullable = false) // rol_id es el nombre real de la columna de este modelo
    private Rol rol;

    @CreatedDate
    @Column(name = "fecha_creacion", updatable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "ultimo_login")
    private LocalDateTime ultimoLogin;

    @Column(nullable = false, columnDefinition = "boolean default true")
    private Boolean activo;

    @Column(name = "intentos_fallidos", columnDefinition = "integer default 0")
    private Integer intentosFallidos;

    @OneToMany(mappedBy = "usuario")
    @Builder.Default
    private List<Documento> documentos = new ArrayList<>();

    @OneToMany(mappedBy = "usuario") // En las relaciones se mapea la columna con el nombre que manejamos en el
    // modelo
    @Builder.Default
    private List<HistorialCambio> historialCambios = new ArrayList<>();

    // Agregado para seguridad para obtener el nombre del rol de mi campo rol
    public String getRolNombre() {
        return rol.getNombre(); // Ejemplo: "ADMIN" o "USER"
    }
}

/*
 * Mapeo: Como los objetos de Java se van a transformar en registros
 * Persistencia: Guardar un objto JAVA como un registro de la base de datos
 */

/**
 * Carga Ansiosa (Eager Loading)
    @ManyToOne(fetch = FetchType.EAGER) // Se completa la relacion 1 to Many Rol -> Usuarios
    @JoinColumn(name = "rol_id", nullable = false) // rol_id es el nombre real de la columna de este modelo
    private Rol rol;

    La entidad Usuario tiene una relación @ManyToOne con Rol (carga perezosa por defecto).
    Cuando CustomUserDetailsService llama a usuario.getRol().getNombre(), la sesión de Hibernate ya está cerrada. 
    Hibernate no puede cargar el objeto Rol (proxy) fuera de una sesión activa.
 */