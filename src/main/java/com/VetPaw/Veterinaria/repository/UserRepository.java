package com.VetPaw.Veterinaria.repository;

import com.VetPaw.Veterinaria.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User,Long> {
    Optional<User> findByDocumento(String documento);
    Optional<User> findByUsername(String username);
}
