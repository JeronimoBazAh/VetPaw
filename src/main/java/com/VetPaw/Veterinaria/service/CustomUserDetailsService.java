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
        Optional<Usuario> usuario = usuarioRepository.findByDocumento(documento);
        if (usuario.isPresent()) {
            Usuario u = usuario.get();
            return User.builder()
                    .username(u.getDocumento())
                    .password(u.getPassword())
                    .roles(u.getRol())
                    .build();
        }
        throw new UsernameNotFoundException("No encontrado: " + documento);
    }
}