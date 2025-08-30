package com.VetPaw.Veterinaria.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Propietario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String apellido;
    private String celular;


    @OneToMany
    @JoinColumn(name="mascota_id")
    private Long IDMascota;
}
