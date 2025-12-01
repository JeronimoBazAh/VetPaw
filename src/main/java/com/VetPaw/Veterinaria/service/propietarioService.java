package com.VetPaw.Veterinaria.service;


import com.VetPaw.Veterinaria.model.Propietario;
import com.VetPaw.Veterinaria.model.Usuario;
import com.VetPaw.Veterinaria.repository.PropietarioRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class propietarioService implements CrudRepository<Propietario, Long> {

    private PropietarioRepository propietarioRepository;


    @Override
    public <S extends Propietario> S save(S entity) {
        return null;
    }

    @Override
    public <S extends Propietario> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Propietario> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public Iterable<Propietario> findAll() {
        return null;
    }

    @Override
    public Iterable<Propietario> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(Propietario entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends Propietario> entities) {

    }

    @Override
    public void deleteAll() {

    }
    public Optional<Propietario> findByDocumento(String documento){
        return propietarioRepository.findByDocumento(documento);
    }

}
