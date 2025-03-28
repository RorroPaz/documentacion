package com.example.reportes.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;

@Entity
@Table(name = "historial_cambios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HistorialCambio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "documento_id", nullable = false)
    private Documento documento;

    @Column(nullable = false, length = 20)
    private String accion; // CREACION, MODIFICACION, ELIMINACION, DESCARGAR, VISTO

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(columnDefinition = "TEXT")
    private String detalles;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "estado_anterior", columnDefinition = "jsonb")
    private String estadoAnterior;

    @Column(name = "fecha_cambio", nullable = false)
    private LocalDateTime fechaCambio;
}