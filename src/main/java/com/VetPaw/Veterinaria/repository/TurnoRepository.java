package com.VetPaw.Veterinaria.repository;

import com.VetPaw.Veterinaria.model.HistorialClinico;
import com.VetPaw.Veterinaria.model.Turno;
import com.VetPaw.Veterinaria.model.Veterinario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface TurnoRepository extends CrudRepository<Turno,Long> {
    @Query("SELECT COUNT(t) > 0 FROM Turno t WHERE t.vet = :vet AND t.fecha = :fecha AND t.hora = :hora")
    boolean existeTurnoSolapado(@Param("vet") Veterinario vet,
                                @Param("fecha") LocalDate fecha,
                                @Param("hora") LocalDateTime hora);
}
