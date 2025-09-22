package com.VetPaw.Veterinaria.service;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service

public interface Service <T>{

    List<T> findAll();
    T save(T x);

    Optional<T> findById(Long id);
    void delete(Long id);


}
