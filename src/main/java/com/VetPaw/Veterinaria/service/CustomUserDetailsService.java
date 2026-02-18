package com.VetPaw.Veterinaria.service;

import com.VetPaw.Veterinaria.model.Propietario;
import com.VetPaw.Veterinaria.model.Usuario;
import com.VetPaw.Veterinaria.model.Veterinario;
import com.VetPaw.Veterinaria.repository.UserRepository;
import com.VetPaw.Veterinaria.repository.veterinarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
            return User.builder()
                    .username(usuario.get().getDocumento())
                    .password(usuario.get().getPassword())
                    .roles(usuario.get().getRol())
                    .build();
        }
        Optional<Veterinario> vet = veterinarioRepository.findByDocumento(documento);
        if (vet.isPresent()) {
            return User.builder()
                    .username(vet.get().getDocumento())
                    .password(vet.get().getPassword())
                    .build();
        }

    throw new UsernameNotFoundException("Usuario no existe ");
    }


/*
        Usuario usuario = usuarioRepository.findByDocumento(documento).orElseThrow(() -> new UsernameNotFoundException(("Usuario no encontrado")));

        return org.springframework.security.core.userdetails.User.builder()
                .username(usuario.getDocumento())
                .password(usuario.getPassword())
                .roles(usuario.getRol())
                .build();
*/


 }

