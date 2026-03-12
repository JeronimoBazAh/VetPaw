package com.VetPaw.Veterinaria.service;


import com.VetPaw.Veterinaria.model.Propietario;
import com.VetPaw.Veterinaria.repository.PropietarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class PropietarioUserDetailsService implements UserDetailsService {

    @Autowired
    private PropietarioRepository propietarioRepository;

    @Override
    public UserDetails loadUserByUsername(String documento) throws UsernameNotFoundException {
        Optional<Propietario> propietario = propietarioRepository.findByDocumento(documento);
        if (propietario.isEmpty()) {  // antes comparabas Optional con null, eso nunca funciona
            throw new UsernameNotFoundException("No encontrado: " + documento);
        }
        return User.builder()
                .username(propietario.get().getDocumento())
                .password(propietario.get().getPassword())
                .roles("CLIENTE")
                .build();
    }
}
