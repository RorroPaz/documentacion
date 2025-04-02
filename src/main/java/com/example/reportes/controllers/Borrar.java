package com.example.reportes.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Borrar {
    @GetMapping("/inicio")
    public String home(Model model){
        // Model , es una interfaz para pasar datos al Front, no almacena datos
        /*
            Recordemos que un Repository tambien es una interfaz extendida de JAP
            Al llamar a una interfaz , Spring crea un proxy que es una clase que implementa esta interfaz
            Despues instancia ese proxy y ese es el objeto que ocupamos

            Entonces instancia un proxy con la interfaz Model y le paso desde el controller un objeto 
            de llamdo model
            
            Por lo tanto Spring me permite acceder a este proceso desde la interfaz y me ahorra codigo
         */
        // model.addAttribute("usuario",new Usuario(null,"Rodrigo Paz")); // Recibe (String, Object)
        return "inicio"; //Retorna el nombre de la vista 
    }
    
}

/*
 * Flujo:
 * A. El usuario escribe "/"
 * B. El Controller lo cacha y lo redirecciona a "home" 
 *      1. Se crea un Model para pasar los datos
 *      2. Se usa .addAttribute para pasar el dato
 *      3. @GetMapping mapea solicitudes HTTP GET 
 *      4. Spring inyecta automáticamente el objeto Model como parámetro
 *
 */
