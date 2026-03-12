package com.VetPaw.Veterinaria.service;

import com.VetPaw.Veterinaria.model.Veterinario;
import com.VetPaw.Veterinaria.repository.veterinarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VeterinarioUserDetailsService implements UserDetailsService {

    @Autowired
    private veterinarioRepository veterinarioRepository;

    @Override
    public UserDetails loadUserByUsername(String documento) throws UsernameNotFoundException {
        Optional<Veterinario> vet = veterinarioRepository.findByDocumento(documento);
        if (vet.isEmpty()) {
            throw new UsernameNotFoundException("No encontrado: " + documento);
        }
        return User.builder()
                .username(vet.get().getDocumento())
                .password(vet.get().getPassword())
                .roles("VETERINARIO")
                .build();
    }
}