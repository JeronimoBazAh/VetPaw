package com.VetPaw.Veterinaria.repository;

import com.VetPaw.Veterinaria.model.Mascota;
import com.VetPaw.Veterinaria.model.Propietario;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface MascotaRepository extends CrudRepository<Mascota,Long> {
    List<Mascota> findByPropietario(Propietario propietario);
}
