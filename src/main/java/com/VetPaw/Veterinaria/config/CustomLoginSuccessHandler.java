package com.VetPaw.Veterinaria.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Set;

@Component
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        // Obtenemos los roles del usuario logueado
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());

        if (roles.contains("ROLE_VETERINARIO")) {
            // Redirige al HTML del Veterinario
            response.sendRedirect("/auth/vetDashboard");
        } else if (roles.contains("ROLE_RECEPCIONISTA")) {
            // Redirige al HTML del Recepcionista/Usuario común
            response.sendRedirect("/auth/inicio");
        } else if (roles.contains("ROLE_ADMIN")) {
            response.sendRedirect("/admin/home");
        } else {
            // Destino por defecto
            response.sendRedirect("/auth/inicio");
        }
    }
}