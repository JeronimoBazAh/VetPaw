package com.VetPaw.Veterinaria.service;

import com.VetPaw.Veterinaria.model.Propietario;
import com.VetPaw.Veterinaria.repository.propietarioRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Optional;


@org.springframework.stereotype.Service
public class PropietarioService implements Service<Propietario> {

    @Autowired
    private propietarioRepository repository;

    public PropietarioService(propietarioRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Propietario> findAll() {
        return List.of();
    }

    @Override
    public Propietario save(Propietario x) {
        return null;
    }

    @Override
    public Optional<Propietario> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public void delete(Long id) {

    }
}
