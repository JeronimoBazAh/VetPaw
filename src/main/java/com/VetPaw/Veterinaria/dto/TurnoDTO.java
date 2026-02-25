package com.VetPaw.Veterinaria.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TurnoDTO {
    private Long idTurno;
    private String estado;
    private LocalDate fecha;
    private LocalDateTime hora;
    private String motivo;
    private String veterinarioNombre;
    private String mascotaNombre;
    private Long mascotaId;

    // Getters y Setters
    public Long getIdTurno() { return idTurno; }
    public void setIdTurno(Long idTurno) { this.idTurno = idTurno; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public LocalDateTime getHora() { return hora; }
    public void setHora(LocalDateTime hora) { this.hora = hora; }

    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }

    public String getVeterinarioNombre() { return veterinarioNombre; }
    public void setVeterinarioNombre(String veterinarioNombre) {
        this.veterinarioNombre = veterinarioNombre;
    }

    public String getMascotaNombre() { return mascotaNombre; }
    public void setMascotaNombre(String mascotaNombre) {
        this.mascotaNombre = mascotaNombre;
    }

    public Long getMascotaId() { return mascotaId; }
    public void setMascotaId(Long mascotaId) { this.mascotaId = mascotaId; }
}
