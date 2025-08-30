package com.VetPaw.Veterinaria.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
public class HistorialClinico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    private Mascota mascota ;

    @ManyToOne
    private Veterinario veterinario;

    private LocalDate fecha;

    private String diagnostico;

    private String medicacion;

    private String otro;
}
