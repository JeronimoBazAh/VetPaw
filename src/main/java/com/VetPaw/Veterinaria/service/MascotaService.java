package com.VetPaw.Veterinaria.service;

import com.VetPaw.Veterinaria.model.Mascota;
import com.VetPaw.Veterinaria.model.Propietario;
import com.VetPaw.Veterinaria.repository.MascotaRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
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
        mascotaRepository.save(x);
        return x;
    }

    @Override
    public Optional<Mascota> findById(Long id) {
        Optional<Mascota> mascota = mascotaRepository.findById(id);
        return mascota;
    }

    @Override
    public void delete(Long id) {

    }

    public List<Mascota> findByPropietario(Propietario documento){
        return mascotaRepository.findByPropietario(documento);
    };

    public List<Mascota> listarTodos() {
        Iterable<Mascota> mascotasIterable = mascotaRepository.findAll();
        List<Mascota> listaCompleta = new ArrayList<>();

        // Esto asegura que CADA elemento del iterable se guarde en la lista
        mascotasIterable.forEach(listaCompleta::add);

        // Agrega este log para ver cuántas trae realmente la base de datos
        System.out.println("DEBUG: Mascotas encontradas en BD: " + listaCompleta.size());

        return listaCompleta;
    }

}
