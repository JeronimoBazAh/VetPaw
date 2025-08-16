package com.VetPaw.Veterinaria.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Mascota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String especie;
    private String raza;

    @ManyToOne
    @JoinColumn(name = "propietario_id")
    private Propietario propietario;



}
