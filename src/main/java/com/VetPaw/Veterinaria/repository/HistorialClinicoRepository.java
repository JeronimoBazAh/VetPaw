package com.VetPaw.Veterinaria.repository;

import com.VetPaw.Veterinaria.model.HistorialClinico;

import com.VetPaw.Veterinaria.model.Mascota;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface HistorialClinicoRepository extends CrudRepository<HistorialClinico,Long> {
    List<HistorialClinico> findByMascota(Mascota mascota);

}
