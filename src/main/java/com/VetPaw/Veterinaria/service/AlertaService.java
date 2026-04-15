package com.VetPaw.Veterinaria.service;


import com.VetPaw.Veterinaria.model.*;
import com.VetPaw.Veterinaria.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service

public class AlertaService {

    @Autowired
    private AlertaRepository alertaRepository;

    @Autowired
    private MascotaService mascotaService;

    @Autowired
    private PropietarioService propietarioService;

    @Autowired
    private TurnoService turnoService;

    // Ejecutar diariamente a las 8:00 AM
    @Scheduled(fixedRate = 120000)
    public void verificarYGenerarAlertas() {
        System.out.println("=== VERIFICANDO ALERTAS - " + LocalDateTime.now() + " ===");

        verificarVacunasPendientes();
        verificarControlesPendientes();
        verificarTratamientosAtrasados();
        actualizarAlertasVencidas();

        System.out.println("=== VERIFICACIÓN COMPLETADA ===");
    }

    private void verificarVacunasPendientes() {
        // Lógica para detectar vacunas próximas o atrasadas
        List<Mascota> mascotas = mascotaService.listarTodos();

        for (Mascota mascota : mascotas) {
            // Ejemplo: Si la mascota tiene más de 12 meses sin vacuna antirrábica
            LocalDate ultimaVacuna = obtenerFechaUltimaVacuna(mascota);

            if (ultimaVacuna != null) {
                long mesesSinVacuna = ChronoUnit.MONTHS.between(ultimaVacuna, LocalDate.now());

                if (mesesSinVacuna >= 12) {
                    crearAlerta(
                            mascota.getPropietario(),
                            mascota,
                            TipoAlerta.VACUNA,
                            "Vacuna antirrábica vencida",
                            "La vacuna antirrábica de " + mascota.getNombre() + " venció hace " +
                                    (mesesSinVacuna - 12) + " meses",
                            LocalDate.now().minusMonths(mesesSinVacuna - 12),
                            Prioridad.ALTA
                    );
                }
            }
        }
    }

    private void verificarControlesPendientes() {
        LocalDate hace30Dias = LocalDate.now().minusDays(30);
        LocalDate proximosSieteDias = LocalDate.now().plusDays(7);

        // Buscar turnos próximos
        List<Turno> turnosProximos = turnoService.findTurnosEntreFechas(
                LocalDate.now(),
                proximosSieteDias
        );

        for (Turno turno : turnosProximos) {
            // Crear alerta recordatoria
            crearAlerta(
                    turno.getMascota().getPropietario(),
                    turno.getMascota(),
                    TipoAlerta.CONTROL,
                    "Próximo control veterinario",
                    "Recordatorio: " + turno.getMascota().getNombre() +
                            " tiene control el " + turno.getFecha(),
                    turno.getFecha(),
                    Prioridad.MEDIA
            );
        }
    }

    private void verificarTratamientosAtrasados() {
    }

    private void actualizarAlertasVencidas() {
        List<Alerta> vencidas = alertaRepository.findAlertasVencidas(LocalDate.now());

        for (Alerta alerta : vencidas) {
            alerta.setEstado(EstadoAlerta.VENCIDA);
            long diasAtraso = ChronoUnit.DAYS.between(
                    alerta.getFechaVencimiento(),
                    LocalDate.now()
            );
            alerta.setDiasAtraso((int) diasAtraso);
            alertaRepository.save(alerta);
        }
    }

    private void crearAlerta(Propietario propietario, Mascota mascota,
                             TipoAlerta tipo, String titulo, String descripcion,
                             LocalDate fechaVencimiento, Prioridad prioridad) {

        Alerta alerta = new Alerta();
        alerta.setPropietario(propietario);
        alerta.setMascota(mascota);
        alerta.setTipo(tipo);
        alerta.setTitulo(titulo);
        alerta.setDescripcion(descripcion);
        alerta.setFechaVencimiento(fechaVencimiento);
        alerta.setFechaCreacion(LocalDateTime.now());
        alerta.setEstado(EstadoAlerta.PENDIENTE);
        alerta.setPrioridad(prioridad);

        alertaRepository.save(alerta);

    }

    private LocalDate obtenerFechaUltimaVacuna(Mascota mascota) {
        return null; // Por ahora
    }

    public List<Alerta> getAlertasPorPropietario(Propietario propietario) {
        return alertaRepository.findByPropietarioOrderByPrioridadDescFechaVencimientoAsc(
                propietario
        );
    }

    public Long contarAlertasPendientes(Propietario propietario) {
        return alertaRepository.countByPropietarioAndEstado(
                propietario,
                EstadoAlerta.PENDIENTE
        );
    }

    public void marcarComoVista(Long alertaId) {
        Alerta alerta = alertaRepository.findById(alertaId).orElse(null);
        if (alerta != null) {
            alerta.setEstado(EstadoAlerta.VISTA);
            alertaRepository.save(alerta);
        }
    }
    public void marcarComoCompletada(Long alertaId) {
        Alerta alerta = alertaRepository.findById(alertaId).orElse(null);
        if (alerta != null) {
            alerta.setEstado(EstadoAlerta.COMPLETADA);
            alertaRepository.save(alerta);
        }
    }
}
