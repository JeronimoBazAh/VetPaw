package com.VetPaw.Veterinaria.service;

import com.VetPaw.Veterinaria.model.Veterinario;
import com.VetPaw.Veterinaria.repository.veterinarioRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
public class VeterinarioService implements Service<Veterinario> {

    @Autowired
    private veterinarioRepository repository;

    public VeterinarioService(veterinarioRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Veterinario> findAll() {
        return List.of();
    }

    @Override
    public Veterinario save(Veterinario x) {
        return null;
    }

    @Override
    public Optional<Veterinario> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public void delete(Long id) {

    }
}
