package com.VetPaw.Veterinaria.service;

import com.VetPaw.Veterinaria.repository.turnoRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
public class TurnoService implements Service<TurnoService>{

    @Autowired
    private turnoRepository repository;

    public TurnoService(turnoRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<TurnoService> findAll() {
        return List.of();
    }

    @Override
    public TurnoService save(TurnoService x) {
        return null;
    }

    @Override
    public Optional<TurnoService> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public void delete(Long id) {

    }
}
