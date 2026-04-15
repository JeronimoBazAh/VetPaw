package com.VetPaw.Veterinaria.controller.api;

import com.VetPaw.Veterinaria.dto.ErrorResponse;
import com.VetPaw.Veterinaria.dto.VacunaDTO;
import com.VetPaw.Veterinaria.model.Mascota;
import com.VetPaw.Veterinaria.model.Propietario;
import com.VetPaw.Veterinaria.model.Vacunacion;
import com.VetPaw.Veterinaria.repository.MascotaRepository;
import com.VetPaw.Veterinaria.repository.PropietarioRepository;
import com.VetPaw.Veterinaria.repository.TurnoRepository;
import com.VetPaw.Veterinaria.repository.VacunaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/vacunas")
@CrossOrigin(origins = "*")
public class ApiVacunas {
    @Autowired
    private TurnoRepository turnoRepository;

    @Autowired
    private PropietarioRepository propietarioRepository;

    @Autowired
    private MascotaRepository mascotaRepository;

    @Autowired
    private VacunaRepository vacunacionRepository;

    @GetMapping("/mis-vacunas")
    public ResponseEntity<?> getMisVacunas(@RequestHeader("Authorization") String token) {
        try {
            String documento = token.replace("Bearer_", "");
            Optional<Propietario> propietario = propietarioRepository.findByDocumento(documento);
            if (propietario.isEmpty()) return ResponseEntity.status(404).body(new ErrorResponse("No encontrado"));

            List<Mascota> mascotas = mascotaRepository.findByPropietario(propietario.get());
            List<VacunaDTO> resultado = new ArrayList<>();

            for (Mascota mascota : mascotas) {
                List<Vacunacion> vacunas = vacunacionRepository.findByMascota(mascota);
                for (Vacunacion v : vacunas) {
                    VacunaDTO dto = new VacunaDTO();
                    dto.setId(v.getId());

                    dto.setNombreVacuna(v.getTipo());
                    dto.setFechaAplicacion(v.getFechaAplicacion() != null ? v.getFechaAplicacion(): null);
                    dto.setFechaProxima(v.getFechaProxima() != null ? v.getFechaProxima(): null);
                    dto.setMascotaNombre(mascota.getNombre());
                    dto.setEstado(v.getObservacion());
                    resultado.add(dto);
                }
            }
            return ResponseEntity.ok(resultado);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ErrorResponse("Error: " + e.getMessage()));
        }
    }

    @GetMapping("/historial")
    public ResponseEntity<?> getHistorial(@RequestHeader("Authorization") String token) {
        try {
            // Asegurate de que el separador (espacio o guion bajo) coincida con tu Login
            String documento = token.replace("Bearer ", "");
            Optional<Propietario> propietario = propietarioRepository.findByDocumento(documento);

            if (propietario.isEmpty()) return ResponseEntity.status(404).body(new ErrorResponse("Dueño no encontrado"));

            List<Mascota> mascotas = mascotaRepository.findByPropietario(propietario.get());
            List<VacunaDTO> resultado = new ArrayList<>();

            for (Mascota mascota : mascotas) {
                List<Vacunacion> vacunas = vacunacionRepository.findByMascota(mascota);
                for (Vacunacion v : vacunas) {
                    // FILTRO CLAVE: Solo agregamos si el estado es APLICADA
                    // Ojo: En tu DTO pusiste dto.setEstado(v.getObservacion())
                    // Asegurate que en 'observacion' diga "APLICADA"
                    if ("APLICADA".equalsIgnoreCase(v.getObservacion())) {
                        VacunaDTO dto = new VacunaDTO();
                        dto.setId(v.getId());
                        dto.setNombreVacuna(v.getTipo());
                        dto.setFechaAplicacion(v.getFechaAplicacion());
                        dto.setMascotaNombre(mascota.getNombre());
                        dto.setEstado(v.getObservacion());
                        resultado.add(dto);
                    }
                }
            }
            return ResponseEntity.ok(resultado);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ErrorResponse("Error: " + e.getMessage()));
        }
    }
    @GetMapping("/mascota/{idMascota}")
    public ResponseEntity<?> getVacunasPorMascota(
            @PathVariable Long idMascota,
            @RequestHeader("Authorization") String token) {
        try {
            System.out.println("=== Buscando vacunas para mascota ID: " + idMascota + " ===");

            Mascota mascota = mascotaRepository.findById(idMascota).orElse(null);
            if (mascota == null) {
                System.out.println("=== Mascota no encontrada ===");
                return ResponseEntity.status(404).body(new ErrorResponse("Mascota no encontrada"));
            }

            System.out.println("=== Mascota encontrada: " + mascota.getNombre() + " ===");

            List<Vacunacion> vacunas = vacunacionRepository.findByMascota(mascota);
            System.out.println("=== Vacunas encontradas: " + vacunas.size() + " ===");

            List<VacunaDTO> resultado = new ArrayList<>();
            for (Vacunacion v : vacunas) {
                System.out.println("=== Vacuna: " + v.getId() + " - " + v.getTipo() + " ===");
                VacunaDTO dto = new VacunaDTO();
                dto.setId(v.getId());
                dto.setNombreVacuna(v.getTipo());
                dto.setFechaAplicacion(v.getFechaAplicacion());
                dto.setFechaProxima(v.getFechaProxima());
                dto.setMascotaNombre(mascota.getNombre());
                dto.setEstado(v.getObservacion());
                resultado.add(dto);
            }

            return ResponseEntity.ok(resultado);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(new ErrorResponse("Error: " + e.getMessage()));
        }
    }

    private VacunaDTO convertir(Vacunacion vacuna){
        VacunaDTO dto = new VacunaDTO();
        dto.setId(vacuna.getId() );
        dto.setMascotaNombre(vacuna.getMascota().getNombre());
        dto.setEstado(vacuna.getTipo());
        dto.setFechaAplicacion(vacuna.getFechaAplicacion());
        dto.setFechaProxima(vacuna.getFechaProxima());
        dto.setNombreVacuna(vacuna.getLote());



        return dto;
    }
}
