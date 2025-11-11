package com.VetPaw.Veterinaria.repository;

import com.VetPaw.Veterinaria.model.Tratamiento;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TratamientoRepository extends CrudRepository<Tratamiento, Long> {

    List<Tratamiento> findByEstado(String estado);
}
