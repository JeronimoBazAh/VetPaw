package com.VetPaw.Veterinaria.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String documento;
    private String nombre;
    private String apellido;
    private String celular;
    private String password;
    private String rol;
    private LocalDate fechaIngreso;
    private LocalDate fechaEgreso;

 @PrePersist
    public void pre(){
     this.fechaIngreso = LocalDate.now();
     this.rol = "Usuario";
 }

}
