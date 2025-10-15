package com.VetPaw.Veterinaria.config;

import com.VetPaw.Veterinaria.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http
                .authorizeHttpRequests(auth -> auth
                        // Rutas públicas (sin autenticación)
                        .requestMatchers("/auth/**", "/public/**", "/css/**", "/js/**", "/images/**", "/error").permitAll()
                        // Rutas protegidas por rol
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/veterinario/**").hasAnyRole("VETERINARIO", "ADMIN")
                        .requestMatchers("/recepcion/**").hasAnyRole("RECEPCIONISTA", "ADMIN")
                        // Cualquier otra ruta requiere autenticación
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/auth/login")                    // Tu página de login personalizada
                        .loginProcessingUrl("/auth/login")           // URL donde Spring procesa el login
                        .defaultSuccessUrl("/gestionTurnos", true)       // Redirige aquí después del login exitoso
                        .failureUrl("/auth/login?error=true")        // Redirige aquí si falla el login
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/auth/logout")                   // URL para cerrar sesión
                        .logoutSuccessUrl("/auth/login?logout=true") // Redirige aquí después del logout
                        .invalidateHttpSession(true)                 // Invalida la sesión
                        .deleteCookies("JSESSIONID")                 // Elimina cookies
                        .permitAll()
                )
                .build();
    }
}

