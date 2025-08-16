package com.VetPaw.Veterinaria.repository;

import com.VetPaw.Veterinaria.model.Turno;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public class TurnoRepository implements CrudRepository<Turno,Long> {

    @Override
    public <S extends Turno> S save(S entity) {
        return null;
    }

    @Override
    public <S extends Turno> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Turno> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public Iterable<Turno> findAll() {
        return null;
    }

    @Override
    public Iterable<Turno> findAllById(Iterable<Long> longs) {
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
    public void delete(Turno entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends Turno> entities) {

    }

    @Override
    public void deleteAll() {

    }
}
