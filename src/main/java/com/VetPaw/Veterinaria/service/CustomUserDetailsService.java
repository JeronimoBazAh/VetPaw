package com.VetPaw.Veterinaria.service;

import com.VetPaw.Veterinaria.model.User;
import com.VetPaw.Veterinaria.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UsuarioRepository usuarioRepository;

    public CustomUserDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> usuario = Optional.ofNullable(usuarioRepository.findAllByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(("Usuario no encontrado"))));

        return org.springframework.security.core.userdetails.User.builder()
                .username(usuario.get().getNombre())
                .password(usuario.get().getPassw())
                .build();





        }
}
