package com.VetPaw.Veterinaria.controller;

import com.VetPaw.Veterinaria.model.*;
import com.VetPaw.Veterinaria.service.*;
import com.fasterxml.jackson.databind.annotation.JsonAppend;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/cliente")
public class clienteController {

    @Autowired
    VeterinarioService vetService;

    @Autowired
    HistorialClinicoService historialClinicoService;
    @Autowired
    UserService userService;

    @Autowired
    private PropietarioService propietarioService;

    @Autowired
    private VacunaService vacunaService;

    @Autowired
    private TratamientoService tratamientoService;



    @GetMapping("/crearCliente")
    public String nuevoCliente(Model model) {

        model.addAttribute("cliente", new Propietario());

        return "clientes/crearCliente";
    }


    @PostMapping("/crearCliente")
    public String crearCliente(@Valid @ModelAttribute("cliente") Propietario cliente, BindingResult result, Model model, RedirectAttributes redirect, SessionStatus status) {

        if (result.hasErrors()) {

            return "clientes/crearCliente";
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
    public String gestionCliente(Model model) {
        List<Propietario> lista = propietarioService.listarTodos();
        model.addAttribute("lista", lista);

        return "/clientes/gestionPropietarios";
    }



    @PostMapping("/buscar")
    public String buscarCliente(@RequestParam("documento") String documento,
                                @RequestParam String vistaOrigen,
                                Model model) {

        Optional<Propietario> existe = propietarioService.findByDocumento(documento);
        List<Veterinario> vets = vetService.listarTodos();
        List<Usuario> receps = userService.findAll();
        if (!model.containsAttribute("vacuna")) {
            model.addAttribute("vacuna", new Vacunacion());
        }

        model.addAttribute("recep", receps);
        model.addAttribute("vets", vets);
        model.addAttribute("mascota", new Mascota());
        model.addAttribute("propietario", new Propietario());
        model.addAttribute("turno", new Turno());

        if (existe.isEmpty()) {
            model.addAttribute("errorBusqueda", "No existe cliente con ese DNI");
            return vistaOrigen;
        }

        Propietario propietario = existe.get();

        // ── Mascotas ──────────────────────────────────────────────
        Map<Long, List<Vacunacion>>      vacunasPorMascota     = new HashMap<>();
        Map<Long, List<Tratamiento>>     tratamientosPorMascota = new HashMap<>();
        Map<Long, List<HistorialClinico>> historialPorMascota   = new HashMap<>();

        for (Mascota m : propietario.getMascotas()) {
            vacunasPorMascota.put(m.getId(),      vacunaService.findByMascota(m));
            tratamientosPorMascota.put(m.getId(), tratamientoService.findByMascota(m));
            historialPorMascota.put(m.getId(),    historialClinicoService.findByMascota(m));
        }

        model.addAttribute("encontrado",            propietario);
        model.addAttribute("mostrarDatos",          true);
        model.addAttribute("mascotas",              propietario.getMascotas());
        model.addAttribute("vacunasPorMascota",     vacunasPorMascota);
        model.addAttribute("tratamientosPorMascota", tratamientosPorMascota);
        model.addAttribute("historialPorMascota",   historialPorMascota);

        return vistaOrigen;
    }


    @GetMapping("/editarCliente/{id}")
    public String mostrarFormulario(@PathVariable Long id, Model model) {
        Optional<Propietario> propietario = propietarioService.findById(id);
        if (propietario.isEmpty()) {
            return "redirect:/cliente/gestionCliente";
        }
        model.addAttribute("propietario", propietario.get());
        return "/clientes/editarCliente";
    }

    @GetMapping("/ver/{id}")
    public String verPropietario(@PathVariable Long id, Model model) {
        Optional<Propietario> propietario = propietarioService.findById(id);
        if (propietario.isEmpty()) {
            return "redirect:/cliente/gestionCliente";
        }
        model.addAttribute("propietario", propietario.get());
        return "clientes/infoCliente";
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

