package com.VetPaw.Veterinaria.controller;

import com.VetPaw.Veterinaria.model.Propietario;
import com.VetPaw.Veterinaria.service.PropietarioService;
import com.VetPaw.Veterinaria.service.Service;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/cliente")
public class clienteController {


    @Autowired
    private PropietarioService propietarioService;

    @GetMapping("/crearCliente")
    public String nuevoCliente(){


        return "crearCliente";
    }

    @GetMapping("/gestionCliente")
    public String gestionCliente(){


        return "/turnos/gestionPropietarios";
    }

    @PostMapping
    public String crearCliente(@Valid @ModelAttribute("cliente")Propietario cliente, BindingResult result, Model model, RedirectAttributes redirect, SessionStatus status){

        if(result.hasErrors()){
            // indicar los campos result.rejectValue();
            return "/crearCliente";
        }
        if(cliente.getDocumento() != null){
            Optional<Propietario> existe = propietarioService.findByDocumento(cliente.getDocumento());
            if(existe.isPresent()){
                result.rejectValue("documento","error.documento","El   documento ya esta registrado");
                return "/crearCliente";
            }else{
                propietarioService.save(cliente);
                status.setComplete();
            }
        }
        redirect.addFlashAttribute("success");





        return "redirect";
    }
}
