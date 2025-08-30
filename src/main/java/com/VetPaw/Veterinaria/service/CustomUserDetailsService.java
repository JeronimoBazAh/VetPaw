package com.VetPaw.Veterinaria.service;

import com.VetPaw.Veterinaria.model.User;
import com.VetPaw.Veterinaria.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private final UserRepository usuarioRepository;

    public CustomUserDetailsService(UserRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String nombre) throws UsernameNotFoundException {
        User usuario = usuarioRepository.findByNombre(nombre).orElseThrow(() -> new UsernameNotFoundException(("Usuario no encontrado")));

        return org.springframework.security.core.userdetails.User.builder()
                .username(usuario.getNombre())
                .password(usuario.getPassw())
                .roles(usuario.getRol())
                .build();





        }
}
