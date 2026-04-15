package com.VetPaw.Veterinaria.dto;

import java.time.LocalDate;

public class VacunaDTO {

    private Long id;
    private String nombreVacuna;
    private LocalDate fechaAplicacion;
    private LocalDate fechaProxima;
    private String mascotaNombre;
    private String estado;

    // Constructores
    public VacunaDTO() {}

    public VacunaDTO(Long id, String nombreVacuna, LocalDate fechaAplicacion,
                     LocalDate fechaProxima, String mascotaNombre, String estado) {
        this.id = id;
        this.nombreVacuna = nombreVacuna;
        this.fechaAplicacion = fechaAplicacion;
        this.fechaProxima = fechaProxima;
        this.mascotaNombre = mascotaNombre;
        this.estado = estado;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombreVacuna() { return nombreVacuna; }
    public void setNombreVacuna(String nombreVacuna) { this.nombreVacuna = nombreVacuna; }

    public LocalDate getFechaAplicacion() { return fechaAplicacion; }
    public void setFechaAplicacion(LocalDate fechaAplicacion) { this.fechaAplicacion = fechaAplicacion; }

    public LocalDate getFechaProxima() { return fechaProxima; }
    public void setFechaProxima(LocalDate fechaProxima) { this.fechaProxima = fechaProxima; }

    public String getMascotaNombre() { return mascotaNombre; }
    public void setMascotaNombre(String mascotaNombre) { this.mascotaNombre = mascotaNombre; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}