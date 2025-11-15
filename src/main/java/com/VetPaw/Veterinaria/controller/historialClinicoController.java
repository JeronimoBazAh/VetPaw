package com.VetPaw.Veterinaria.controller;

import com.VetPaw.Veterinaria.model.Tratamiento;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/clinica")
public class historialClinicoController {

    @GetMapping("/historial")
    public String cargarHistorial(){

        return "/clinico/nuevoHistorial";
    }

    @GetMapping("/vacunacion")
    public String cargaVacuna(){


        return "/clinico/nuevoVacunacion";
    }


}
