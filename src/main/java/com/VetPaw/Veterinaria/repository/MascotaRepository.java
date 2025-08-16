package com.VetPaw.Veterinaria.repository;

import com.VetPaw.Veterinaria.model.Mascota;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public class MascotaRepository implements CrudRepository<Mascota,Long> {
    @Override
    public <S extends Mascota> S save(S entity) {
        return null;
    }

    @Override
    public <S extends Mascota> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Mascota> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public Iterable<Mascota> findAll() {
        return null;
    }

    @Override
    public Iterable<Mascota> findAllById(Iterable<Long> longs) {
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
    public void delete(Mascota entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends Mascota> entities) {

    }

    @Override
    public void deleteAll() {

    }
}
