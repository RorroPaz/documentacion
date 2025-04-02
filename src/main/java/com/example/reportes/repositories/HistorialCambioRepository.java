package com.example.reportes.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.reportes.models.HistorialCambio;

public interface HistorialCambioRepository extends JpaRepository<HistorialCambio, Integer> {
    @Query("SELECT h FROM HistorialCambio h WHERE jsonb_extract_path_text(h.estadoAnterior, 'usuario') = :valor")
    List<HistorialCambio> buscarPorCampoEnEstadoAnterior(String valor);
    /**
     * Query para Cuando necesites buscar registros en un campo específico dentro del JSON
     * 
     * Ejemplo:
     * {
        "usuario": "admin",
        "accion": "MODIFICACION",
        "detalles": {"tipo": "PDF"}
        "campo"
        }

        Por lo tanto, si le paso PDF:
        Busca registros donde detalles.tipo sea "PDF"
        List<HistorialCambio> resultados = repo.buscarPorCampoEnEstadoAnterior("admin");
     */
}