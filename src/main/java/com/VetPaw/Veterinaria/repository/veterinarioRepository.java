package com.VetPaw.Veterinaria.repository;

import com.VetPaw.Veterinaria.model.Veterinario;
import org.springframework.data.repository.CrudRepository;

public interface veterinarioRepository extends CrudRepository<Veterinario,Long> {
}
