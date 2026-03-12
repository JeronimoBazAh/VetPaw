package com.VetPaw.Veterinaria.repository;


import com.VetPaw.Veterinaria.model.Alerta;
import com.VetPaw.Veterinaria.model.EstadoAlerta;
import com.VetPaw.Veterinaria.model.Propietario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.time.LocalDate;
import java.util.List;

public interface AlertaRepository extends JpaRepository<Alerta, Long> {

    List<Alerta> findByPropietarioAndEstadoIn(
            Propietario propietario,
            List<EstadoAlerta> estados
    );

    List<Alerta> findByPropietarioOrderByPrioridadDescFechaVencimientoAsc(
            Propietario propietario
    );

    @Query("SELECT a FROM Alerta a WHERE a.fechaVencimiento < :fecha AND a.estado = 'PENDIENTE'")
    List<Alerta> findAlertasVencidas(LocalDate fecha);

    Long countByPropietarioAndEstado(Propietario propietario, EstadoAlerta estado);
}