package com.VetPaw.Veterinaria.service;

import com.VetPaw.Veterinaria.model.Mascota;
import com.VetPaw.Veterinaria.model.Vacunacion;
import com.VetPaw.Veterinaria.repository.VacunaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VacunaService implements com.VetPaw.Veterinaria.service.Service<Vacunacion> {

    @Autowired
    private VacunaRepository vacunaRepository;

    @Override
    public List<Vacunacion> findAll() {
        return (List<Vacunacion>) vacunaRepository.findAll();
    }

    @Override
    public Vacunacion save(Vacunacion x) {
        return vacunaRepository.save(x);
    }

    @Override
    public Optional<Vacunacion> findById(Long id) {
        return vacunaRepository.findById(id);
    }

    @Override
    public void delete(Long id) {

    }

    public List<Vacunacion> findByMascota(Mascota mascota) {
        return vacunaRepository.findByMascota(mascota);
    }
}
