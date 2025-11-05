package com.VetPaw.Veterinaria.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/administracion")
public class turnoController {

    @GetMapping("/turnos")
    public String turnos(){


        return "/turnos/gestionTurnos";
    }

    @GetMapping("/crearTurno")
    public String crearTurno(){


        return "/turnos/crearTurno";
    }

}
