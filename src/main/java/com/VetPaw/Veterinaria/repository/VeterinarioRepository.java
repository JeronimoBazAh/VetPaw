package com.VetPaw.Veterinaria.repository;

import com.VetPaw.Veterinaria.model.Veterinario;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public class VeterinarioRepository implements CrudRepository<Veterinario,Long> {
    @Override
    public <S extends Veterinario> S save(S entity) {
        return null;
    }

    @Override
    public <S extends Veterinario> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Veterinario> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public Iterable<Veterinario> findAll() {
        return null;
    }

    @Override
    public Iterable<Veterinario> findAllById(Iterable<Long> longs) {
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
    public void delete(Veterinario entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends Veterinario> entities) {

    }

    @Override
    public void deleteAll() {

    }
}
