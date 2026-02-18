package com.VetPaw.Veterinaria.controller;

import com.VetPaw.Veterinaria.model.Mascota;
import com.VetPaw.Veterinaria.model.Propietario;
import com.VetPaw.Veterinaria.model.Turno;
import com.VetPaw.Veterinaria.service.PropietarioService;
import com.VetPaw.Veterinaria.service.VeterinarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.print.DocFlavor;
import java.util.Optional;

@Controller
@RequestMapping("/cliente")
public class clienteController {

    @Autowired
    VeterinarioService vetService;

    @Autowired
    private PropietarioService propietarioService;

    @GetMapping("/crearCliente")
    public String nuevoCliente(Model model) {

        model.addAttribute("cliente", new Propietario());

        return "clientes/crearCliente";
    }


    @PostMapping("/crearCliente")
    public String crearCliente(@Valid @ModelAttribute("cliente") Propietario cliente, BindingResult result, Model model, RedirectAttributes redirect, SessionStatus status) {

        if (result.hasErrors()) {

            return "/crearCliente";
        }
        if (cliente.getDocumento() != null) {
            Optional<Propietario> existe = propietarioService.findByDocumento(cliente.getDocumento());
            if (existe.isPresent()) {
                result.rejectValue("documento", "error.documento", "El   documento ya esta registrado");
                return "clientes/crearCliente";
            }

            cliente.setPassword(cliente.getDocumento());
            propietarioService.save(cliente);
            status.setComplete();

        }
        redirect.addFlashAttribute("success", "Propietario registrado");


        return "redirect:/cliente/crearCliente";
    }

    @GetMapping("/gestionCliente")
    public String gestionCliente() {


        return "/turnos/gestionPropietarios";
    }



    @PostMapping("/buscar")
    public String buscarCliente(@RequestParam("documento") String documento,@RequestParam String vistaOrigen ,Model model, RedirectAttributes redirect, String direccion) {
        Optional<Propietario> existe = propietarioService.findByDocumento(documento);

        model.addAttribute("mascota", new Mascota());
        model.addAttribute("propietario", new Propietario());
        model.addAttribute("turno", new Turno());
        model.addAttribute("vets", vetService.findAll());
        if (existe.isEmpty()) {
            model.addAttribute("errorBusqueda","No existe cliente con ese DNI");
            System.out.println("no encontro");
            System.out.println(vistaOrigen);
            return vistaOrigen;


        }else{
            model.addAttribute("encontrado", existe.get());
            model.addAttribute("mostrarDatos", true);
            System.out.println("Encontro");
            System.out.println(vistaOrigen);
            return vistaOrigen;
        }

    }






    /*



    @PostMapping("/buscar")
    public String buscarCliente(@RequestParam("documento") String documento, Model model, RedirectAttributes redirect) {
        Optional<Propietario> existe = propietarioService.findByDocumento(documento);

        model.addAttribute("mascota", new Mascota());
        model.addAttribute("propietario", new Propietario());
        if (existe.isEmpty()) {
            model.addAttribute("errorBusqueda","No existe cliente con ese DNI");
            System.out.println("no encontro");

        }else{
            model.addAttribute("encontrado", existe.get());
            model.addAttribute("mostrarDatos", true);
            System.out.println("Encontro");
        }

        return "encontro";
    }

     */




}

