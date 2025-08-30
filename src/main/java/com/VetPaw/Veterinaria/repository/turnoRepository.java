package com.VetPaw.Veterinaria.repository;

import com.VetPaw.Veterinaria.model.HistorialClinico;
import org.springframework.data.repository.CrudRepository;

public interface turnoRepository extends CrudRepository<HistorialClinico,Long> {
}
