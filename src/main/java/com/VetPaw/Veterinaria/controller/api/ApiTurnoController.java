package com.VetPaw.Veterinaria.controller.api;


import com.VetPaw.Veterinaria.dto.ErrorResponse;
import com.VetPaw.Veterinaria.dto.TurnoDTO;
import com.VetPaw.Veterinaria.model.Mascota;
import com.VetPaw.Veterinaria.model.Propietario;
import com.VetPaw.Veterinaria.model.Turno;
import com.VetPaw.Veterinaria.repository.MascotaRepository;
import com.VetPaw.Veterinaria.repository.PropietarioRepository;
import com.VetPaw.Veterinaria.repository.TurnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/turnos")
@CrossOrigin(origins = "*")
public class ApiTurnoController {

    @Autowired
    private TurnoRepository turnoRepository;

    @Autowired
    private PropietarioRepository propietarioRepository;

    @Autowired
    private MascotaRepository mascotaRepository;

    // Obtener todos los turnos del propietario (todas sus mascotas)
    @GetMapping("/mis-turnos")
    public ResponseEntity<?> getMisTurnos(@RequestHeader("Authorization") String token) {
        try {
            String documento = token.replace("Bearer_", "");
            System.out.println("=== Buscando turnos para documento: " + documento + " ===");

            Optional<Propietario> propietario = propietarioRepository.findByDocumento(documento);

            if (propietario == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ErrorResponse("Propietario no encontrado"));
            }

            List<Mascota> mascotas = mascotaRepository.findByPropietario(propietario.get());
            List<TurnoDTO> turnosDTO = new ArrayList<>();

            for (Mascota mascota : mascotas) {
                List<Turno> turnos = turnoRepository.findByMascota(mascota);

                for (Turno turno : turnos) {
                    TurnoDTO dto = convertirADTO(turno);
                    turnosDTO.add(dto);
                }
            }

            System.out.println("✓ Turnos encontrados: " + turnosDTO.size());
            return ResponseEntity.ok(turnosDTO);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Error: " + e.getMessage()));
        }
    }

    // Obtener turnos de una mascota específica
    @GetMapping("/mascota/{idMascota}")
    public ResponseEntity<?> getTurnosPorMascota(
            @PathVariable Long idMascota,
            @RequestHeader("Authorization") String token) {
        try {
            String documento = token.replace("Bearer_", "");
            Optional<Propietario> propietario = propietarioRepository.findByDocumento(documento);

            if (propietario == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ErrorResponse("Propietario no encontrado"));
            }

            Mascota mascota = mascotaRepository.findById(idMascota).orElse(null);

            if (mascota == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ErrorResponse("Mascota no encontrada"));
            }

            // Verificar que la mascota pertenezca al propietario
            if (!mascota.getPropietario().getId().equals(propietario.get().getId())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(new ErrorResponse("Esta mascota no te pertenece"));
            }

            List<Turno> turnos = turnoRepository.findByMascota(mascota);
            List<TurnoDTO> turnosDTO = new ArrayList<>();

            for (Turno turno : turnos) {
                turnosDTO.add(convertirADTO(turno));
            }

            return ResponseEntity.ok(turnosDTO);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Error: " + e.getMessage()));
        }
    }

    // Método auxiliar para convertir Turno a DTO
    private TurnoDTO convertirADTO(Turno turno) {
        TurnoDTO dto = new TurnoDTO();
        dto.setIdTurno(turno.getIdTurno());
        dto.setEstado(turno.getEstado());
        dto.setFecha(turno.getFecha());
        dto.setHora(turno.getHora());
        dto.setMotivo(turno.getMotivo());
        dto.setMascotaNombre(turno.getMascota().getNombre());
        dto.setMascotaId(turno.getMascota().getId());

        if (turno.getVet() != null) {
            // Asumiendo que Veterinario tiene nombre y apellido
            dto.setVeterinarioNombre(turno.getVet().getNombre() + " " + turno.getVet().getApellido());
        }

        return dto;
    }
}
