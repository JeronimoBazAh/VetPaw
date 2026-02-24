package com.VetPaw.Veterinaria.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Data
@Entity
public class Tratamiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String descripcion;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private int frecuenciaHoras;
    private LocalDateTime ultimaAplicacion;
    private LocalDateTime proximaDosis;
    private String estado;

    @ManyToOne
    @JoinColumn(name = "mascota_id")
    private Mascota mascota;

    @ManyToOne
    @JoinColumn(name = "veterinario_id")
    private Veterinario veterinario;



    @Transient
    public boolean estaAtrasado(){
        LocalDateTime proxima = getProximaDosis();
        return proxima != null && LocalDateTime.now().isAfter(proxima); //comapra dos momentos en el tiempo para determinar si es posterior al otro
    }

    @Transient
    public long getHorasAtraso(){
        if(!estaAtrasado()){
            return 0;
        }
        return ChronoUnit.HOURS.between(getProximaDosis(), LocalDateTime.now());
    }
}
