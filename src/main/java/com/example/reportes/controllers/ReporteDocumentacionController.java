package com.example.reportes.controllers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import com.example.reportes.pojo.DocumentacionData;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.servlet.http.HttpServletRequest;

@RestController
public class ReporteDocumentacionController {
    @PostMapping("/generar-reporte")
    public ResponseEntity<byte[]> generarReporte(@RequestBody DocumentacionData data) throws Exception {
        // Cargar el reporte
        /*
         * No usa JRLoader: * JasperReport jasperReport = (JasperReport)
         * JRLoader.loadObject(reportStream);
         * El InputStream se pasa directamente a JasperFillManager.fillReport().
         * JasperReports internamente maneja la carga del .jasper.
         * 
         * 
         */
        // Cargar el reporte principal compilado
        // InputStream es la forma estándar de Java para leer recursos
        try {
            System.out.println(data.toString());
            System.out.println("1. Iniciando generación de reporte...");

            // Cargar reporte principal
            System.out.println("2. Cargando reporte principal...");
            InputStream principalStream = getClass().getResourceAsStream("/reportes/documento_modulo.jasper");
            if (principalStream == null) {
                System.err.println(
                        "ERROR: No se pudo cargar el reporte principal. Verifica la ruta: /reportes/documento_modulo.jasper");
                throw new FileNotFoundException("Reporte principal no encontrado");
            }
            System.out.println("✓ Reporte principal cargado correctamente");

            // Preparar parámetros
            System.out.println("3. Preparando parámetros...");
            System.out.println("desarrollador: " + data.getDesarrollador());
            System.out.println("nombreModulo: " + data.getNombreModulo());
            System.out.println("rutaModulo: " + data.getRutaModulo());

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("desarrollador", data.getDesarrollador());
            parameters.put("nombreModulo", data.getNombreModulo());
            parameters.put("rutaModulo", data.getRutaModulo());
            parameters.put("clasesUtiliza", data.getClasesUtiliza());
            parameters.put("javascript", data.getJavascript());
            parameters.put("jsp", data.getJsp());
            parameters.put("pojo", data.getPojo());
            parameters.put("serviceRepo", data.getServiceRepo());
            parameters.put("controladores", data.getControladores());
            System.out.println("✓ Parámetros básicos configurados");

            // Configurar datasource para subreportes
            System.out.println("4. Preparando datasource para APIs...");
            System.out.println("Cantidad de APIs encontradas: " + data.getApis().size());
            if (data.getApis().isEmpty()) {
                System.out.println("ADVERTENCIA: La lista de APIs está vacía");
            }

            JRBeanCollectionDataSource apisDataSource = new JRBeanCollectionDataSource(data.getApis());
            parameters.put("apisDataSource", apisDataSource);
            System.out.println("✓ Datasource de APIs configurado");

            // Cargar subreporte
            System.out.println("5. Cargando subreporte...");
            InputStream subStream = getClass().getResourceAsStream("/reportes/subreporte.jasper");
            if (subStream == null) {
                System.err.println(
                        "ERROR: No se pudo cargar el subreporte. Verifica la ruta: /reportes/subreporte.jasper");
                throw new FileNotFoundException("Subreporte no encontrado");
            }
            parameters.put("subreporteApi", subStream);
            System.out.println("✓ Subreporte cargado correctamente");

            // Generar reporte
            System.out.println("6. Generando reporte...");
            JasperPrint jasperPrint = JasperFillManager.fillReport(principalStream, parameters,
                    new JREmptyDataSource());
            System.out.println("✓ Reporte generado en memoria");

            // Exportar a PDF
            System.out.println("7. Exportando a PDF...");
            byte[] pdfBytes = JasperExportManager.exportReportToPdf(jasperPrint);
            System.out.println("✓ PDF generado (" + pdfBytes.length + " bytes)");

            // Cerrar streams
            System.out.println("8. Cerrando recursos...");
            principalStream.close();
            subStream.close();
            System.out.println("✓ Recursos liberados");

            System.out.println("9. Enviando respuesta...");
            return ResponseEntity.ok()
                    .header("Content-Type", "application/pdf")
                    .header("Content-Disposition", "attachment; filename=documentacion_modulo.pdf")
                    .body(pdfBytes);

        } catch (JRException e) {
            System.err.println("ERROR en JasperReports: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error al generar el reporte", e);
        } catch (IOException e) {
            System.err.println("ERROR de IO: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error al leer/escribir recursos", e);
        } catch (Exception e) {
            System.err.println("ERROR inesperado: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error general", e);
        }
    }

    @PostMapping("/debug-json")
    public void debugJson(HttpServletRequest request, @RequestBody(required = false) String rawJson) {
        System.out.println("=== Headers recibidos ===");
        Collections.list(request.getHeaderNames()).forEach(headerName -> {
            System.out.println(headerName + ": " + request.getHeader(headerName));
        });

        System.out.println("=== JSON recibido ===");
        System.out.println(rawJson);

        System.out.println("=== Método HTTP ===");
        System.out.println(request.getMethod());
    }

}
