package com.VetPaw.Veterinaria.controller;

import com.VetPaw.Veterinaria.model.Vacunacion;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mascota")
public class mascotaController {

    @GetMapping("/inicio")
    public String inicioMascota(Model model){



        return "vistaPrincipal.html";
    }
}
