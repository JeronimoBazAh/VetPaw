package com.VetPaw.Veterinaria.service;

import com.VetPaw.Veterinaria.model.Mascota;
import com.VetPaw.Veterinaria.model.Propietario;
import com.VetPaw.Veterinaria.model.Tratamiento;
import com.VetPaw.Veterinaria.repository.TratamientoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TratamientoMonitorService {
    /*

    private final TratamientoRepository tratamientoRepository;

    private final WhatsAppService whatsAppService;


    public void monitorearTratamientos(){
        log.info("Iniciando monitoreo de tratamientos");
        List<Tratamiento> activos = tratamientoRepository.findByEstado("ACTIVO");
        int alertasEnviadas = 0;

        for(Tratamiento tratamiento : activos){
            if(verificarYAlertar(tratamiento)){
                alertasEnviadas++;
            }
        }

    }
    private boolean verificarYAlertar(Tratamiento tratamiento) {
        // Verificar si está atrasado
        if (!tratamiento.estaAtrasado()) {
            return false;
        }

        long horasAtraso = tratamiento.getHorasAtraso();

        // Obtener datos para la alerta
        Mascota mascota = tratamiento.getMascota();
        Propietario propietario = mascota.getPropietario();

        // Enviar alerta por WhatsApp
        boolean enviado = whatsAppService.enviarAlertaTratamiento(
                propietario.getCelular(),
                propietario.getNombre() + " " + propietario.getApellido(),
                mascota.getNombre(),
                tratamiento.getNombre(),
                horasAtraso
        );

        if (enviado) {
            log.info("📱 Alerta enviada: {} - {} - Atraso: {}h",
                    propietario.getNombre(),
                    tratamiento.getNombre(),
                    horasAtraso);
        } else {
            log.error("❌ Error enviando alerta: {} - {}",
                    propietario.getNombre(),
                    tratamiento.getNombre());
        }

        return enviado;
    }
    public void ejecutarMonitoreoManual() {
        log.info("🔧 Ejecutando monitoreo manual...");
        monitorearTratamientos();
    }

     */
}
