package com.VetPaw.Veterinaria.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Vacunacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long Id;

    private Long IdPropietario;

    private Long IdMascota;

    private Long IdVeterinario;

    private String tipo;
    private LocalDate fechaAplicacion;
    private LocalDate fehcaProxima;
    private String lote;
    private String observacion;

}
