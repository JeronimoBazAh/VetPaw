package com.VetPaw.Veterinaria.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String documento;
    private String nombre;
    private String apellido;
    private String celular;
    private String passw;
    private String rol;
    private LocalDate fechaIngreso;
    private LocalDate fechaEgreso;



}
