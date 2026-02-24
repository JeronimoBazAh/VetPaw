package com.VetPaw.Veterinaria.service;

import com.VetPaw.Veterinaria.model.Usuario;
import com.VetPaw.Veterinaria.model.Veterinario;
import com.VetPaw.Veterinaria.repository.UserRepository;
import com.VetPaw.Veterinaria.repository.veterinarioRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository usuarioRepository;
    private final veterinarioRepository veterinarioRepository;

    public CustomUserDetailsService(UserRepository usuarioRepository, veterinarioRepository veterinarioRepository) {
        this.usuarioRepository = usuarioRepository;
        this.veterinarioRepository = veterinarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String documento) throws UsernameNotFoundException {

        // 1. Intentar buscar en la tabla de Usuarios (Recepcionistas, Admin, etc.)
        Optional<Usuario> usuario = usuarioRepository.findByDocumento(documento);
        if (usuario.isPresent()) {
            Usuario u = usuario.get();
            return User.builder()
                    .username(u.getDocumento())
                    .password(u.getPassword())
                    .roles(u.getRol()) // Spring añadirá el prefijo ROLE_ automáticamente
                    .build();
        }

        // 2. Si no se encuentra, intentar buscar en la tabla de Veterinarios
        Optional<Veterinario> vet = veterinarioRepository.findByDocumento(documento);
        if (vet.isPresent()) {
            Veterinario v = vet.get();
            return User.builder()
                    .username(v.getDocumento())
                    .password(v.getPassword())
                    .roles("VETERINARIO") // Asignamos el rol necesario para las rutas protegidas
                    .build();
        }

        // 3. Si no existe en ninguna de las tablas
        throw new UsernameNotFoundException("Usuario no encontrado con documento: " + documento);
    }
}