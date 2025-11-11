package com.VetPaw.Veterinaria.service;

import com.VetPaw.Veterinaria.model.Tratamiento;

import java.util.List;
import java.util.Optional;

public class TratamientoService implements Service<Tratamiento> {
    @Override
    public List<Tratamiento> findAll() {
        return List.of();
    }

    @Override
    public Tratamiento save(Tratamiento x) {
        return null;
    }

    @Override
    public Optional<Tratamiento> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public void delete(Long id) {

    }
}
