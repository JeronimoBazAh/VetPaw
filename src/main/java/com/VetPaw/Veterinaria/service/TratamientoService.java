package com.VetPaw.Veterinaria.service;

import com.VetPaw.Veterinaria.model.Mascota;
import com.VetPaw.Veterinaria.model.Tratamiento;
import com.VetPaw.Veterinaria.repository.TratamientoRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
public class TratamientoService implements Service<Tratamiento> {
    @Autowired
    private TratamientoRepository repository;

    @Override
    public List<Tratamiento> findAll() {
        return (List<Tratamiento>) repository.findAll();
    }

    @Override
    public Tratamiento save(Tratamiento x) {
        return repository.save(x);
    }

    @Override
    public Optional<Tratamiento> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public void delete(Long id) {

    }

    public List<Tratamiento> findByMascota(Mascota mascota){
        return repository.findByMascota(mascota);
    }

}
