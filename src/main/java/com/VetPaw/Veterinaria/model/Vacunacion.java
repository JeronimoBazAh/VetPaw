package com.VetPaw.Veterinaria.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Vacunacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private Long IdPropietario;

    @ManyToOne
    @JoinColumn(name = "mascota_id")
    private Mascota mascota;

    private Long IdVeterinario;

    private String tipo;
    private LocalDate fechaAplicacion;
    private LocalDate fechaProxima;
    private String lote;
    private String observacion;

}
