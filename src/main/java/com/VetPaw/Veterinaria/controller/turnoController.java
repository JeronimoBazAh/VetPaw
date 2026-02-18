package com.VetPaw.Veterinaria.controller;

import com.VetPaw.Veterinaria.model.Turno;
import com.VetPaw.Veterinaria.model.Veterinario;
import com.VetPaw.Veterinaria.service.VeterinarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/administracion")
public class turnoController {

    @Autowired
    VeterinarioService vetService;

    @GetMapping("/turnos")
    public String turnos(){


        return "/turnos/agendaTurnos";
    }



    @GetMapping("/crearTurno")
    public String crearTurno(Model model){
        model.addAttribute("turno", new Turno());
        model.addAttribute("vets", vetService.findAll());


        return "turnos/nuevoTurno";
    }
    @GetMapping("/reportes")
    public String reportes(){


        return "reporteEstadistica";
    }

    @PostMapping
    public String crearTurno(){



        return "turnos/nuevoTurno";
    }



}
