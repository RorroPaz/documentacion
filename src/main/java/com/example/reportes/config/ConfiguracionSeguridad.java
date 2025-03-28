package com.example.reportes.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class ConfiguracionSeguridad {

    /*
        .authorizeHttpRequests(autorizar -> autorizar
            Se declara el controlador principal , que es el /login 
            Se declara que el rol ADMINISTRADOR tenga acceso a las apis "/admin/** *
            Se declara que el rol ADMINISTRADOR y USUARIO tenga acceso a las apis "/documentos/**
    
        .formLogin(formulario -> formulario
            Se declara la loginPage 
            Se declara si es Success, te enviara a /inicio
            Se declara si es Fail, enviara la variable error al login
            Declara que el login es para todos

        .logout(cierreSesion -> cierreSesion
            URL que activa el cierre de sesión (POST), no es ni Controlador , ni una pagina
            Se declara si es Success el logout, envia true al login para confirmar el coerre de sesion
            Se elimina la sesion
            Se eliminan las cookie

        .rememberMe(recordar -> recordar
            Se guarda la clave 
            Se establece el tiempo de duracion de la clave, de un dia

        .sessionManagement(session -> session
            Se declara el numero de sesiones abiertas en el mismo navegador
            Se manda el valor expired al login

        .exceptionHandling(excepciones -> excepciones
            Se manda al controlador acceso denegado /acceso-denegado
*/
    

    @Bean
    public SecurityFilterChain filtroSeguridad(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(autorizar -> autorizar
                .requestMatchers("/login", "/auth/register", "/css/**", "/js/**", "/webjars/**").permitAll()
                .requestMatchers("/admin/**").hasRole("ADMINISTRADOR")
                .requestMatchers("/documentos/**").hasAnyRole("ADMINISTRADOR", "USUARIO")
                .anyRequest().authenticated()
            )
            .formLogin(formulario -> formulario
                .loginPage("/login") // Convención de nombres para organizar tus archivos
                .defaultSuccessUrl("/inicio", true) // Redirige siempre aquí tras login exitoso
                .failureUrl("/login?error=true") // Muestra error en la misma página, le pasa la variable error como true
                .permitAll()
            )
            .logout(cierreSesion -> cierreSesion
                .logoutUrl("/logout") // URL que activa el cierre de sesión (POST)
                .logoutSuccessUrl("/login?logout=true") // Confirma cierre de sesión, le pasa la variable logout a login con el valor true
                .invalidateHttpSession(true) // Elimina la sesión
                .deleteCookies("JSESSIONID")// Borra la cookie
            ) 
            .rememberMe(recordar -> recordar
                .key("claveUnicaYSecreta123")
                .tokenValiditySeconds(86400) // 1 día
            )
            .sessionManagement(session -> session
                 .maximumSessions(1)
                 .expiredUrl("/login?expired")
            )
            .exceptionHandling(excepciones -> excepciones
                .accessDeniedPage("/acceso-denegado")
            );
        return http.build();
    }

    @Bean
    public PasswordEncoder codificadorContrasena() {
        return new BCryptPasswordEncoder();
    }
}


// /webjars/ Librerías frontend empaquetadas como JARs (ej: Bootstrap, FontAwesome).