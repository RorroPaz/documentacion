package com.example.reportes.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiData {
    private String nombreApi;
    private String tipo;
    private String ruta;
    private String datosRegresa;
    private String parametros;
    private String descripcion;
    private String comentariosAdicionales;
}
