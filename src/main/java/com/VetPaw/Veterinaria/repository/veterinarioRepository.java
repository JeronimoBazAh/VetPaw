package com.VetPaw.Veterinaria.repository;

import com.VetPaw.Veterinaria.model.Veterinario;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface veterinarioRepository extends CrudRepository<Veterinario,Long> {
    Optional<Veterinario> findByDocumento(String documento);

}
