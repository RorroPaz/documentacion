package com.example.reportes.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.reportes.services.CustomUserDetailsService;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class ConfiguracionSeguridad {
    // Entender cómo Spring Security maneja el proceso de autenticación es
    // fundamental para trabajar con seguridad en aplicaciones Spring Boot.
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Bean
    public SecurityFilterChain filtroSeguridad(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(autorizar -> autorizar
                .requestMatchers("/register","/login").permitAll()
                .requestMatchers("/admin/**").hasRole("ADMINISTRADOR")
                .requestMatchers("/documentos/**").hasAnyRole("ADMINISTRADOR", "USUARIO") //Permite el acceso a cualquier api /documentos/
                .anyRequest().authenticated()
            )
            .formLogin(formulario -> formulario
                .loginPage("/login") // @GetMapping("/login") Página personalizada de login
                .loginProcessingUrl("/login") // @PostMapping("/login") Procesa el formulario
                .defaultSuccessUrl("/documentos/dashboard", true) // Redirección post-login exitoso
                .failureUrl("/login?error=true") // Manejo de errores
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout") // URL que activa el cierre de sesión (POST) lo maneja spring
                .logoutSuccessUrl("/login?logout=true") // Confirma cierre de sesión, le pasa la variable logout a login con el valor true
                .invalidateHttpSession(true) // Elimina la sesión
                .deleteCookies("JSESSIONID")// Borra la cookie
                .clearAuthentication(true) //Limpia la autenticacion
                .permitAll()
            ) 
            .sessionManagement(session -> session
                 .maximumSessions(1)
                 .expiredUrl("/login?expired=true")
            )
            .exceptionHandling(excepciones -> excepciones
                .accessDeniedPage("/acceso-denegado")
            )
            .csrf(csrf -> csrf
                .ignoringRequestMatchers("/register")
            );
        return http.build();
    }

    @Bean
    public PasswordEncoder codificadorContrasena() {
        return new BCryptPasswordEncoder();
    }

    // Agrega este método en la misma clase
    @Bean
    public FilterRegistrationBean<Filter> noCacheFilter() {
        FilterRegistrationBean<Filter> filterRegBean = new FilterRegistrationBean<>();
        filterRegBean.setFilter(new Filter() {
            @Override
            public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) 
                throws IOException, ServletException {
                HttpServletResponse response = (HttpServletResponse) res;
                response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
                response.setHeader("Pragma", "no-cache");
                response.setHeader("Expires", "0");
                chain.doFilter(req, res);
            }
        });
        filterRegBean.addUrlPatterns("/dashboard", "/documentos", "/usuarios","/register", "/usuarios");
        return filterRegBean;
    }
}

// /webjars/ Librerías frontend empaquetadas como JARs (ej: Bootstrap,
// FontAwesome).

/*
 * .authorizeHttpRequests(autorizar -> autorizar
 * Se declara el controlador principal , que es el /login
 * Se declara que el rol ADMINISTRADOR tenga acceso a las apis "/admin/** *
 * Se declara que el rol ADMINISTRADOR y USUARIO tenga acceso a las apis
 * "/documentos/**
 * 
 * .formLogin(formulario -> formulario
 * Se declara la loginPage
 * Se declara si es Success, te enviara a /inicio
 * Se declara si es Fail, enviara la variable error al login
 * Declara que el login es para todos
 * 
 * .logout(cierreSesion -> cierreSesion
 * URL que activa el cierre de sesión (POST), no es ni Controlador , ni una
 * pagina
 * Se declara si es Success el logout, envia true al login para confirmar el
 * coerre de sesion
 * Se elimina la sesion
 * Se eliminan las cookie
 * 
 * .rememberMe(recordar -> recordar
 * Se guarda la clave
 * Se establece el tiempo de duracion de la clave, de un dia
 * 
 * .sessionManagement(session -> session
 * Se declara el numero de sesiones abiertas en el mismo navegador
 * Se manda el valor expired al login
 * 
 * .exceptionHandling(excepciones -> excepciones
 * Se manda al controlador acceso denegado /acceso-denegado
 */
