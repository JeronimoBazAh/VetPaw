package com.VetPaw.Veterinaria.controller.api;

import com.VetPaw.Veterinaria.dto.ErrorResponse;
import com.VetPaw.Veterinaria.dto.LoginDTO;
import com.VetPaw.Veterinaria.model.Propietario;
import com.VetPaw.Veterinaria.repository.PropietarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class ApiAuthController {

    @Autowired
    private PropietarioRepository propietarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("API REST funcionando!");
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginPropietario(@RequestBody LoginDTO.LoginRequest request) {
        try {
            System.out.println("=== LOGIN PROPIETARIO (APP MÓVIL) ===");
            System.out.println("Documento: " + request.getDocumento());

            Optional<Propietario> propietario = propietarioRepository.findByDocumento(request.getDocumento());

            if (propietario == null) {
                System.out.println("✗ Propietario no encontrado");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ErrorResponse("Documento o contraseña incorrectos"));
            }

            if (propietario.get().getPassword() == null ||
                    !passwordEncoder.matches(request.getPassword(), propietario.get().getPassword())) {
                System.out.println("✗ Contraseña incorrecta");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ErrorResponse("Documento o contraseña incorrectos"));
            }

            String token = "Bearer_" + request.getDocumento();
            String nombreCompleto = propietario.get().getNombre() + " " + propietario.get().getApellido();

            System.out.println("✓ Login exitoso: " + nombreCompleto);

            return ResponseEntity.ok(new LoginDTO.LoginResponse(token, nombreCompleto));

        } catch (Exception e) {
            System.out.println("✗ Error inesperado: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Error en el servidor: " + e.getMessage()));
        }
    }

    // ===== ENDPOINT TEMPORAL PARA ENCRIPTAR CONTRASEÑAS =====
    // ⚠️ BORRAR DESPUÉS DE USARLO EN PRODUCCIÓN
    @GetMapping("/encriptar-password")
    public ResponseEntity<?> encriptarPassword(
            @RequestParam String documento,
            @RequestParam String passwordPlano) {
        try {
            System.out.println("=== ENCRIPTANDO PASSWORD ===");
            System.out.println("Documento: " + documento);

            Optional<Propietario> propietario = propietarioRepository.findByDocumento(documento);

            if (propietario == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ErrorResponse("Propietario no encontrado"));
            }

            // Encriptar la contraseña
            String passwordEncriptado = passwordEncoder.encode(passwordPlano);
            propietario.get().setPassword(passwordEncriptado);
            propietarioRepository.save(propietario.get());

            System.out.println("✓ Password encriptado y guardado");
            System.out.println("Hash: " + passwordEncriptado);

            return ResponseEntity.ok(new SuccessResponse(
                    "Password encriptado exitosamente para documento: " + documento
            ));

        } catch (Exception e) {
            System.out.println("✗ Error: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Error: " + e.getMessage()));
        }
    }
}

// Clase auxiliar para respuesta exitosa
class SuccessResponse {
    private String message;

    public SuccessResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
