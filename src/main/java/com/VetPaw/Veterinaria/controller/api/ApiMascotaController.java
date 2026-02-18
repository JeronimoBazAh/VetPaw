package com.VetPaw.Veterinaria.controller.api;

import com.VetPaw.Veterinaria.dto.ErrorResponse;
import com.VetPaw.Veterinaria.dto.MascotaDTO;
import com.VetPaw.Veterinaria.model.Mascota;
import com.VetPaw.Veterinaria.model.Propietario;
import com.VetPaw.Veterinaria.repository.MascotaRepository;
import com.VetPaw.Veterinaria.repository.PropietarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/mascotas")
@CrossOrigin(origins = "*")
public class ApiMascotaController {

    @Autowired
    private MascotaRepository mascotaRepository;

    @Autowired
    private PropietarioRepository propietarioRepository;

    @GetMapping("/mis-mascotas")
    public ResponseEntity<?> getMisMascotas(
            @RequestHeader("Authorization") String token) {
        try {
            // Extraer documento del token
            String documento = token.replace("Bearer_", "");
            System.out.println("=== Buscando mascotas para: " + documento + " ===");

            // Buscar propietario
            Optional<Propietario> propietario = propietarioRepository.findByDocumento(documento);

            if (propietario == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ErrorResponse("Propietario no encontrado"));
            }

            // Obtener mascotas
            List<Mascota> mascotas = mascotaRepository.findByPropietario(propietario.get());

            // Convertir a DTO
            List<MascotaDTO> mascotasDTO = new ArrayList<>();
            for (Mascota m : mascotas) {
                MascotaDTO dto = new MascotaDTO();
                dto.setId(m.getId());
                dto.setNombre(m.getNombre());
                dto.setEspecie(m.getEspecie());
                dto.setRaza(m.getRaza());
                dto.setColor(m.getColor());
                dto.setPeso(m.getPeso() != null ? m.getPeso().doubleValue() : 0.0);

                // Calcular edad
                if (m.getFechaNacimiento() != null) {
                    Period periodo = Period.between(m.getFechaNacimiento(), LocalDate.now());
                    dto.setEdad(periodo.getYears());
                } else {
                    dto.setEdad(m.getEdad() != null ? m.getEdad().intValue() : 0);
                }

                mascotasDTO.add(dto);
            }

            System.out.println("✓ Mascotas encontradas: " + mascotasDTO.size());
            return ResponseEntity.ok(mascotasDTO);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Error: " + e.getMessage()));
        }
    }
}
