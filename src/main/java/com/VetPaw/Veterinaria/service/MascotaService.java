package com.VetPaw.Veterinaria.service;

import com.VetPaw.Veterinaria.model.Mascota;
import com.VetPaw.Veterinaria.repository.MascotaRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
public class MascotaService implements Service<Mascota>{

    @Autowired
    private MascotaRepository mascotaRepository;

    public MascotaService(MascotaRepository mascotaRepository) {
        this.mascotaRepository = mascotaRepository;
    }

    @Override
    public List<Mascota> findAll() {
        return List.of();
    }

    @Override
    public Mascota save(Mascota x) {
        return null;
    }

    @Override
    public Optional<Mascota> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public void delete(Long id) {

    }
}
