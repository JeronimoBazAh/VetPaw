package com.VetPaw.Veterinaria.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller("/mascota")
public class mascotaController {

    @GetMapping("/inicio")
    public String inicioMascota(Model model){



        return "algo.html";
    }
}
