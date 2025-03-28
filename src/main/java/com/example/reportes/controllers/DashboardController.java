package com.example.reportes.controllers;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model; // ¡Este es el import correcto!
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @GetMapping("/dashboard")
    public String dashboard(Model model) { // Ahora sí reconocerá addAttribute()
        // Datos de ejemplo
        // model.addAttribute("usuario", new Usuario(null,"Rodrigo Paz"));
        
        Map<String, Integer> stats = new HashMap<>();
        stats.put("totalDocumentos", 124);
        stats.put("documentosPendientes", 12);
        stats.put("documentosAprobados", 98);
        
        model.addAttribute("stats", stats);
        return "dashboard";
    }
}