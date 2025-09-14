package com.VetPaw.Veterinaria.repository;

import com.VetPaw.Veterinaria.model.Usuario;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<Usuario,Long> {
    Optional<Usuario> findByDocumento(String documento);
    Optional<Usuario> findByNombre(String username);
}
