package com.VetPaw.Veterinaria.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
public class Turno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTurno;

    @ManyToOne
    @JoinColumn(name = "idVeterinario")
    private Veterinario vet;

    @ManyToOne
    @JoinColumn(name = "idMascota")
    private Mascota mascota;

    @ManyToOne
    @JoinColumn(name = "idUsuario")
    private Usuario recep;

    private String estado;
    private LocalDate fecha;
    private LocalDateTime hora;
    private LocalDate fechaSolicitud;
    private String motivo;



}
