package com.VetPaw.Veterinaria.service;

import com.VetPaw.Veterinaria.model.Turno;
import com.VetPaw.Veterinaria.model.Veterinario;
import com.VetPaw.Veterinaria.repository.TurnoRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
public class TurnoService implements Service<Turno>{

    @Autowired
    private TurnoRepository repository;

    public TurnoService(TurnoRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Turno> findAll() {
        return List.of();
    }

    @Override
    public Turno save(Turno x) {
        return repository.save(x);
    }

    @Override
    public Optional<Turno> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public void delete(Long id) {

    }
    public boolean existeTurnoSolapado(Veterinario vet, LocalDate fecha, LocalDateTime hora) {
        return repository.existeTurnoSolapado(vet, fecha, hora);
    }
}
