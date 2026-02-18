package com.VetPaw.Veterinaria.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Veterinario {

    public Veterinario(Long id, String documento, String celular, LocalDate fechaSalida, String estado, LocalDate fechaIngreso, String nombre, String apellido) {
        this.id = id;
        this.documento = documento;
        this.celular = celular;
        this.fechaSalida = fechaSalida;
        this.estado = estado;
        this.fechaIngreso = fechaIngreso;
        this.nombre = nombre;
        this.apellido = apellido;
    }

    public Veterinario() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String documento;
    private String nombre;
    private String apellido;
    private String celular;
    private LocalDate fechaIngreso;
    private LocalDate fechaSalida;
    private String estado;
    private String password;

    @PrePersist
    public void pre(){
        this.fechaIngreso = LocalDate.now();
    }


    @PreUpdate
    public void estado(){

        if(this.estado == "INACTIVO" && this.fechaSalida == null){
            this.fechaSalida = LocalDate.now();
        }
    }


}
