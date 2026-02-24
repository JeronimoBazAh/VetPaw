package com.VetPaw.Veterinaria.service;

import com.VetPaw.Veterinaria.model.Veterinario;
import com.VetPaw.Veterinaria.repository.veterinarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
public class VeterinarioService implements Service<Veterinario> {

    private PasswordEncoder passwordEncoder;

    @Autowired
    private veterinarioRepository veterinarioRepository;

    @Autowired
    public VeterinarioService(veterinarioRepository veterinarioRepository, PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        this.veterinarioRepository = veterinarioRepository;
    }
    @Autowired
    private veterinarioRepository repository;


    @Override
    public List<Veterinario> findAll() {
        return (List<Veterinario>) veterinarioRepository.findAll();

    }

    @Override
    public Veterinario save(Veterinario x) {
        return veterinarioRepository.save(x);
    }

    @Override
    public Optional<Veterinario> findById(Long id) {
        return veterinarioRepository.findById(id);
    }

    @Override
    public void delete(Long id) {

    }
    public Veterinario registrarUsuario(Veterinario vet){
        if(repository.findByDocumento(vet.getDocumento()).isPresent()){
            throw new IllegalArgumentException("El documento ya esta registrado");
        }
        vet.setPassword(passwordEncoder.encode(vet.getPassword()));

        return repository.save(vet);
    }

    public List<Veterinario> listarTodos() {
        List<Veterinario> listaCompleta = new ArrayList<>();
        veterinarioRepository.findAll().forEach(listaCompleta::add);
        return listaCompleta;
    }


}
