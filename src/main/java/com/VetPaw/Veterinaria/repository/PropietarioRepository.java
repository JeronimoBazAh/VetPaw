package com.VetPaw.Veterinaria.repository;

import com.VetPaw.Veterinaria.model.Propietario;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface PropietarioRepository extends CrudRepository<Propietario,Long> {
     Optional<Propietario> findByDocumento(String doc);
}
