package com.VetPaw.Veterinaria.repository;

import com.VetPaw.Veterinaria.model.Mascota;
import com.VetPaw.Veterinaria.model.Propietario;
import com.VetPaw.Veterinaria.model.Vacunacion;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface VacunaRepository extends CrudRepository<Vacunacion,Long> {
    List<Vacunacion> findByMascota(Mascota mascota);
}
