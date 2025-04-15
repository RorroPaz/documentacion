package com.example.reportes.pojo;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DocumentacionData {
    private String desarrollador;
    private String nombreModulo;
    private String rutaModulo;
    private String clasesUtiliza;
    private String javascript;
    private String jsp;
    private String pojo;
    private String serviceRepo;
    private String controladores;
    private List<ApiData> apis = new ArrayList<>();
}
