package com.VetPaw.Veterinaria.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
public class Alerta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "propietario_id")
    private Propietario propietario;

    @ManyToOne
    @JoinColumn(name = "mascota_id")
    private Mascota mascota;

    @Enumerated(EnumType.STRING)
    private TipoAlerta tipo; // VACUNA, TRATAMIENTO, CONTROL, DESPARASITACION

    private String titulo;
    private String descripcion;
    private LocalDate fechaVencimiento;
    private LocalDateTime fechaCreacion;

    @Enumerated(EnumType.STRING)
    private EstadoAlerta estado; // PENDIENTE, VISTA, COMPLETADA, VENCIDA

    @Enumerated(EnumType.STRING)
    private Prioridad prioridad; // BAJA, MEDIA, ALTA, URGENTE

    private Integer diasAtraso; // Null si no está atrasado
}




