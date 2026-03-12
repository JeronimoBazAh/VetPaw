package com.VetPaw.Veterinaria.repository;

import com.VetPaw.Veterinaria.model.Mascota;
import com.VetPaw.Veterinaria.model.Turno;
import com.VetPaw.Veterinaria.model.Veterinario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;



public interface TurnoRepository extends JpaRepository<Turno, Long> {

    List<Turno> findByMascota(Mascota mascota);

    List<Turno> findByFechaBetween(LocalDate fechaInicio, LocalDate fechaFin);

    List<Turno> findByMascotaAndFechaBetween(
            Mascota mascota,
            LocalDate fechaInicio,
            LocalDate fechaFin
    );

    @Query("SELECT t FROM Turno t WHERE t.fecha >= :hoy AND t.fecha <= :fechaLimite AND t.estado = :estado")
    List<Turno> findTurnosProximos(
            @Param("hoy") LocalDate hoy,
            @Param("fechaLimite") LocalDate fechaLimite,
            @Param("estado") String estado
    );

    List<Turno> findByMascotaAndEstado(Mascota mascota, String estado);

    @Query("SELECT COUNT(t) > 0 FROM Turno t WHERE t.vet = :vet AND t.fecha = :fecha AND t.hora = :hora")
    boolean existsByVetAndFechaAndHora(
            @Param("vet") Veterinario vet,
            @Param("fecha") LocalDate fecha,
            @Param("hora") LocalDateTime hora
    );
}

