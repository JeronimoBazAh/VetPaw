package com.VetPaw.Veterinaria.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
public class Mascota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String especie;
    private String raza;
    private String nombre;
    private LocalDate fechaNacimiento;
    private Long edad;
    private String color;
    private Long peso;
    private String sexo;
    private LocalDate fechaIngreso;



    @ManyToOne
    @JoinColumn(name = "propietario_id")
    private Propietario propietario;



}
