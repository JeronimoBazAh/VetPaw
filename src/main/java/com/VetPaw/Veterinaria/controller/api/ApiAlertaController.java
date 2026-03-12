package com.VetPaw.Veterinaria.controller.api;

import com.VetPaw.Veterinaria.dto.AlertaDTO;
import com.VetPaw.Veterinaria.dto.CountResponse;
import com.VetPaw.Veterinaria.dto.ErrorResponse;
import com.VetPaw.Veterinaria.model.Alerta;
import com.VetPaw.Veterinaria.model.Propietario;
import com.VetPaw.Veterinaria.repository.PropietarioRepository;
import com.VetPaw.Veterinaria.service.AlertaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/alertas")
@CrossOrigin(origins = "*")
public class ApiAlertaController {

    @Autowired
    private AlertaService alertaService;

    @Autowired
    private PropietarioRepository propietarioRepository;

    // Obtener todas las alertas del propietario
    @GetMapping("/mis-alertas")
    public ResponseEntity<?> getMisAlertas(@RequestHeader("Authorization") String token) {
        try {
            String documento = token.replace("Bearer ", "").replace("Bearer_", "").trim();
            System.out.println("=== Buscando alertas para: " + documento + " ===");

            Optional<Propietario> propietario = propietarioRepository.findByDocumento(documento);

            if (propietario.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ErrorResponse("Propietario no encontrado"));
            }

            List<Alerta> alertas = alertaService.getAlertasPorPropietario(propietario.get());
            List<AlertaDTO> alertasDTO = new ArrayList<>();

            for (Alerta alerta : alertas) {
                alertasDTO.add(convertirADTO(alerta));
            }

            System.out.println("✓ Alertas encontradas: " + alertasDTO.size());
            return ResponseEntity.ok(alertasDTO);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Error: " + e.getMessage()));
        }
    }

    // Contar alertas pendientes
    @GetMapping("/count-pendientes")
    public ResponseEntity<?> countAlertasPendientes(
            @RequestHeader("Authorization") String token) {
        try {
            String documento = token.replace("Bearer ", "").replace("Bearer_", "").trim();
            Optional<Propietario> propietario = propietarioRepository.findByDocumento(documento);

            if (propietario == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ErrorResponse("Propietario no encontrado"));
            }

            Long count = alertaService.contarAlertasPendientes(propietario.get());
            return ResponseEntity.ok(new CountResponse(count));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Error: " + e.getMessage()));
        }
    }

    // Marcar alerta como vista
    @PutMapping("/{id}/marcar-vista")
    public ResponseEntity<?> marcarComoVista(@PathVariable Long id) {
        try {
            alertaService.marcarComoVista(id);
            return ResponseEntity.ok(new SuccessResponse("Alerta marcada como vista"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Error: " + e.getMessage()));
        }
    }

    // Marcar alerta como completada
    @PutMapping("/{id}/marcar-completada")
    public ResponseEntity<?> marcarComoCompletada(@PathVariable Long id) {
        try {
            alertaService.marcarComoCompletada(id);
            return ResponseEntity.ok(new SuccessResponse("Alerta marcada como completada"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Error: " + e.getMessage()));
        }
    }

    // Método auxiliar para convertir a DTO
    private AlertaDTO convertirADTO(Alerta alerta) {
        AlertaDTO dto = new AlertaDTO();
        dto.setId(alerta.getId());
        dto.setTipo(alerta.getTipo().toString());
        dto.setTitulo(alerta.getTitulo());
        dto.setDescripcion(alerta.getDescripcion());
        dto.setFechaVencimiento(alerta.getFechaVencimiento());
        dto.setFechaCreacion(alerta.getFechaCreacion());
        dto.setEstado(alerta.getEstado().toString());
        dto.setPrioridad(alerta.getPrioridad().toString());
        dto.setDiasAtraso(alerta.getDiasAtraso());
        dto.setMascotaNombre(alerta.getMascota().getNombre());
        dto.setMascotaId(alerta.getMascota().getId());
        return dto;
    }
}

