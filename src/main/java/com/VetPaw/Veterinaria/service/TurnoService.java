package com.VetPaw.Veterinaria.service;

import com.VetPaw.Veterinaria.model.Mascota;
import com.VetPaw.Veterinaria.model.Turno;
import com.VetPaw.Veterinaria.model.Veterinario;
import com.VetPaw.Veterinaria.repository.TurnoRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
public class TurnoService implements Service<Turno>{

    @Autowired
    private TurnoRepository repository;

    public TurnoService(TurnoRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Turno> findAll() {
        return List.of();
    }

    @Override
    public Turno save(Turno x) {
        return repository.save(x);
    }

    @Override
    public Optional<Turno> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public void delete(Long id) {

    }
    public List<Turno> findTurnosProximos(LocalDate fechaLimite, String estado) {
        return repository.findTurnosProximos(
                LocalDate.now(),
                fechaLimite,
                estado
        );
    }

    public boolean existeTurnoSolapado(Veterinario vet, LocalDate fecha, LocalDateTime hora) {
        return repository.existsByVetAndFechaAndHora(vet, fecha, hora);
    }
    public List<Turno> findTurnosEntreFechas(LocalDate fechaInicio, LocalDate fechaFin) {
        return repository.findByFechaBetween(fechaInicio, fechaFin);
    }
    public List<Turno> findTurnosPendientes(Mascota mascota) {
        return repository.findByMascotaAndEstado(mascota, "PROGRAMADO");
    }
    public List<Turno> findTurnosPorMes(int mes, int año) {
        LocalDate inicio = LocalDate.of(año, mes, 1);
        LocalDate fin = inicio.withDayOfMonth(inicio.lengthOfMonth());
        return repository.findByFechaBetween(inicio, fin);
    }
}
