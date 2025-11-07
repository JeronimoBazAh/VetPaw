package com.VetPaw.Veterinaria.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller("/clinica")
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
