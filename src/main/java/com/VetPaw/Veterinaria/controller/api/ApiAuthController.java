package com.VetPaw.Veterinaria.controller.api;

import com.VetPaw.Veterinaria.dto.ErrorResponse;
import com.VetPaw.Veterinaria.dto.LoginDTO.LoginRequest;
import com.VetPaw.Veterinaria.dto.LoginDTO.LoginResponse;
import com.VetPaw.Veterinaria.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class ApiAuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("API REST funcionando correctamente!");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            System.out.println("=== API LOGIN LLAMADO ===");
            System.out.println("Documento: " + request.getDocumento());

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getDocumento(),
                            request.getPassword()
                    )
            );

            UserDetails userDetails = userDetailsService
                    .loadUserByUsername(request.getDocumento());

            String token = "Bearer_" + request.getDocumento();
            String nombreUsuario = userDetails.getUsername();

            System.out.println("✓ Login exitoso: " + nombreUsuario);

            return ResponseEntity.ok(new LoginResponse(token, nombreUsuario));

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse("Documento o contraseña incorrectos"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Error: " + e.getMessage()));
        }
    }
}