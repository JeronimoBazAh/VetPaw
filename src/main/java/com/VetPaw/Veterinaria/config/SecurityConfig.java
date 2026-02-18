package com.VetPaw.Veterinaria.config;

import com.VetPaw.Veterinaria.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

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
                // Habilitar CORS para la app móvil
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))

                // Deshabilitar CSRF solo para rutas de API
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/api/**")  // API sin CSRF
                )

                .authorizeHttpRequests(auth -> auth
                        // API pública para la app móvil
                        .requestMatchers("/api/auth/**").permitAll()

                        // Rutas públicas web (sin autenticación)
                        .requestMatchers("/auth/**", "/public/**", "/css/**", "/js/**", "/images/**", "/error").permitAll()

                        // API protegida (requiere token)
                        .requestMatchers("/api/**").authenticated()

                        // Rutas web protegidas por rol
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/veterinario/**").hasAnyRole("VETERINARIO", "ADMIN")
                        .requestMatchers("/recepcion/**").hasAnyRole("RECEPCIONISTA", "ADMIN")

                        // Cualquier otra ruta requiere autenticación
                        .anyRequest().authenticated()
                )

                // Configuración de login para la WEB
                .formLogin(form -> form
                        .loginPage("/auth/login")
                        .usernameParameter("documento")
                        .passwordParameter("password")
                        .loginProcessingUrl("/auth/login")
                        .defaultSuccessUrl("/auth/inicio", true)
                        .failureUrl("/auth/login?error=true")
                        .permitAll()
                )

                // Configuración de logout para la WEB
                .logout(logout -> logout
                        .logoutUrl("/auth/logout")
                        .logoutSuccessUrl("/auth/login?logout=true")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                )

                .build();
    }

    // Configuración de CORS para permitir peticiones desde la app móvil
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(Arrays.asList("*"));  // Permitir todas las IPs
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}