package com.VetPaw.Veterinaria.service;

import com.VetPaw.Veterinaria.model.User;

import java.util.List;
import java.util.Optional;

public interface Service <T>{

    List<T> findAll();
    T save(T x);

    Optional<T> findById(Long id);
    void delete(Long id);


}
