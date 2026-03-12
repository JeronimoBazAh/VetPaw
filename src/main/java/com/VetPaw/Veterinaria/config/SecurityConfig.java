package com.VetPaw.Veterinaria.config;

import com.VetPaw.Veterinaria.service.CustomUserDetailsService;
import com.VetPaw.Veterinaria.service.PropietarioUserDetailsService;
import com.VetPaw.Veterinaria.service.VeterinarioUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private VeterinarioUserDetailsService veterinarioUserDetailsService;

    @Autowired
    private CustomLoginSuccessHandler successHandler;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private PropietarioUserDetailsService propietarioUserDetailsService;

    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider usuarioProvider = new DaoAuthenticationProvider();
        usuarioProvider.setUserDetailsService(customUserDetailsService);
        usuarioProvider.setPasswordEncoder(passwordEncoder());

        DaoAuthenticationProvider veterinarioProvider = new DaoAuthenticationProvider();
        veterinarioProvider.setUserDetailsService(veterinarioUserDetailsService);
        veterinarioProvider.setPasswordEncoder(passwordEncoder());

        DaoAuthenticationProvider propietarioProvider = new DaoAuthenticationProvider();
        propietarioProvider.setUserDetailsService(propietarioUserDetailsService);
        propietarioProvider.setPasswordEncoder(passwordEncoder());

        return new ProviderManager(List.of(usuarioProvider, veterinarioProvider, propietarioProvider));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/api/**")
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/auth/**", "/public/**", "/css/**", "/js/**", "/images/**", "/error").permitAll()
                        .requestMatchers("/api/**").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/veterinario/**").hasAnyRole("VETERINARIO", "ADMIN")
                        .requestMatchers("/recepcion/**").hasAnyRole("RECEPCIONISTA", "ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/auth/login")
                        .usernameParameter("documento")
                        .passwordParameter("password")
                        .loginProcessingUrl("/auth/login")
                        .successHandler(successHandler)
                        .failureUrl("/auth/login?error=true")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/auth/logout")
                        .logoutSuccessUrl("/auth/login?logout=true")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                )
                .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }


}