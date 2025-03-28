package com.example.reportes.pojos;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class GenerarHash {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        
        // Generar hash para "Admin123"
        String hashAdmin = encoder.encode("Rodrigo");
        System.out.println("Hash para Admin123: " + hashAdmin);
        
        // Generar hash para "User1234"
        String hashUser = encoder.encode("User1234");
        System.out.println("Hash para User1234: " + hashUser);
    }
}