package com.VetPaw.Veterinaria.service;

import com.VetPaw.Veterinaria.model.HistorialClinico;
import com.VetPaw.Veterinaria.model.Mascota;
import com.VetPaw.Veterinaria.repository.HistorialClinicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HistorialClinicoService implements com.VetPaw.Veterinaria.service.Service<HistorialClinico> {
    @Autowired
    private HistorialClinicoRepository repo;

    @Override
    public List<HistorialClinico> findAll() {
        return (List<HistorialClinico>) repo.findAll();
    }

    @Override
    public HistorialClinico save(HistorialClinico x) {
        return repo.save(x);
    }

    @Override
    public Optional<HistorialClinico> findById(Long id) {
        return repo.findById(id);
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }

    public List<HistorialClinico> findByMascota(Mascota mascota) {
        return repo.findByMascota(mascota);
    }
}
