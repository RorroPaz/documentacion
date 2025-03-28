package com.example.reportes.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles")
@Data // Lombok: incluye @Getter, @Setter, @ToString, etc
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true, length = 20) 
    private String nombre;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private String permisos; // JSON format: {"puede_editar":true, "modulos":["finanzas"]}

    @OneToMany(mappedBy = "rol") // Un Rol puede estar asignado a muchos Usuarios // En las realciones se maneja el nombre que tenemos en el modelo de la tabla
    @Builder.Default
    private Set<Usuario> usuarios = new HashSet<>();
    // mappedBy="rol": Indica que la propiedad rol en la clase Usuario es la dueña
    // de la relación.
    // Crea una relación bidireccional donde:
    /*
     * El lado @ManyToOne (en Usuario) es el dueño
     * 
     * El lado @OneToMany (en Rol) es el inverso
     */
}

//  ROLES (nombre, permisos)
//  ('ADMINISTRADOR', '{"documentos": ["crear", "leer", "actualizar", "eliminar", "descargar"], "usuarios": ["gestionar"]}'),
//  ('USUARIO', '{"documentos": ["crear", "leer", "descargar"]}');

//  '{
//     "documentos": ["crear", "leer", "actualizar", "eliminar", "descargar"], 
//     "usuarios": ["gestionar"]
// }'

// '{
//     "documentos": ["crear", "leer", "descargar"]
// }'