package com.VetPaw.Veterinaria.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cliente")
public class clienteController {

    @GetMapping("/crearCliente")
    public String crearCliente(){


        return "crearCliente";
    }

    @GetMapping("/gestionCliente")
    public String gestionCliente(){


        return "/turnos/gestionPropietarios";
    }
}
