package com.example.reportes.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "documentos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Documento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 150)
    private String nombre;

    @Column(columnDefinition = "TEXT") // Si algun campo es Text aqui se pone
    private String descripcion;

    @Lob
    @Column(name = "contenido_pdf", nullable = false, columnDefinition = "BYTEA") // Especifica BYTEA explícitamente
    private byte[] contenidoPdf;

    @Column(name = "hash_archivo", length = 64)
    private String hashArchivo;

    @Column(nullable = false) //Este en la BD es BigInt not null
    private Long tamano;

    @Column(name = "tipo_mime", length = 50, columnDefinition = "varchar(50) default 'application/pdf'")
    private String tipoMime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario; // Se crea una declaracion de Campo

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "modulo_id")
    private Modulo modulo;

    @CreationTimestamp
    @Column(name = "fecha_creacion", updatable = false)
    private LocalDateTime fechaCreacion;

    @UpdateTimestamp
    @Column(name = "fecha_modificacion")
    private LocalDateTime fechaModificacion;

    @Column(name = "version_documento", length = 20, columnDefinition = "varchar(20) default '1.0'") // columnDefinition es igual a -> DEFAULT '1.0' en SQL
    private String versionDocumento;

    @OneToMany(mappedBy = "documento", cascade = CascadeType.ALL)
    @Builder.Default
    private List<HistorialCambio> historialCambios = new ArrayList<>();
}