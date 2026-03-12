package com.VetPaw.Veterinaria.controller;

import com.VetPaw.Veterinaria.model.Mascota;
import com.VetPaw.Veterinaria.model.Propietario;
import com.VetPaw.Veterinaria.model.Vacunacion;
import com.VetPaw.Veterinaria.service.MascotaService;
import com.VetPaw.Veterinaria.service.PropietarioService;
import com.VetPaw.Veterinaria.service.VacunaService;
import com.VetPaw.Veterinaria.service.VeterinarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

@Controller
@RequestMapping("/mascota")
public class mascotaController {

    @Autowired
    private VeterinarioService serviceVeterinario;

    @Autowired
    private MascotaService serviceMascota;

    @Autowired
    private PropietarioService servicePropietario;

    @Autowired
    private VacunaService serviceVacuna;


    @GetMapping("/crear")
    public String crearMascota(Model model){
        model.addAttribute("mascota", new Mascota());
        model.addAttribute("propietario", new Propietario());
        return "clinico/registrarMascota";
    }



    @PostMapping("/crear")
    public String registrar(@Valid @ModelAttribute("mascota") Mascota mascota, BindingResult result, @RequestParam("propietarioid") Long propietarioid, Model model, RedirectAttributes redirect, SessionStatus status){

        if(result.hasErrors()){
            model.addAttribute("error", "Error en los campos del formulario");
            return "redirect:/mascota/crear";
        }

        //String mensaje = "La mascota ha sido registrada con exito ";

        Optional<Propietario> prop = servicePropietario.findById(propietarioid);
        if(prop.isPresent()){
            mascota.setPropietario(prop.get());

            serviceMascota.save(mascota);
            status.setComplete();
            model.addAttribute("exito", "La mascota ha sido registrada exitosamente");
            System.out.println("supuestamente guardo");
            return "clinico/registrarMascota";



        }else{
            model.addAttribute("errorm", "Error, la mascota no ha podido ser guardada");

            redirect.addFlashAttribute("Error", "Propietario no encontrado");
            System.out.println("no guardo");
            return "clinico/registrarMascota";
        }


    }

    @GetMapping("/gestion")
    public String gestionMascota(@RequestParam(required = false, defaultValue = "") String dni,Model model){
        Optional<Propietario> propietario = servicePropietario.findByDocumento(dni);
        List<Mascota> mascotas;
        if (!dni.isBlank()) {
            mascotas = serviceMascota.findByPropietario(propietario.get());
        } else {
            mascotas = new ArrayList<>();
        }

        model.addAttribute("mascotas", mascotas);
        model.addAttribute("dni", dni);
        return "clinico/gestionMascotas";
    }

    @GetMapping("/vacunacion")
    public String vecunacion(){


        return "clinico/nuevoVacunacion";
    }

    @GetMapping("/tratamiento")
    public String tratamiento(){


        return "clinico/tratamientos";
    }

    @GetMapping("/historial")
    public String clinico(){


        return "clinico/nuevoHistorial";
    }

    @GetMapping("/detalle/{id}")
    public String detalle(@PathVariable Long id, Model model) {
        Optional<Mascota> mascota = serviceMascota.findById(id);

        if (mascota.isPresent()) {
            model.addAttribute("mascota", mascota.get());
            return "clinico/detalleMascota";
        } else {
            return "redirect:/mascota/gestion";
        }
    }


    // VACUNACION

    @PostMapping("/nuevaVacuna")
    public String nuevaVacuna(
            @Valid @ModelAttribute("vacuna") Vacunacion vacuna,
            BindingResult result,
            @RequestParam("propietarioid") Long propietarioid,
            @RequestParam("idMascota") Long idMascota,
            Model model,
            SessionStatus status) {

        if (result.hasErrors()) {
            model.addAttribute("errort", "Error en los campos del formulario");
            recargarModelo(propietarioid, model);
            return "clinico/nuevoVacunacion";
        }

        Optional<Propietario> prop = servicePropietario.findById(propietarioid);

        if (prop.isPresent()) {
            Mascota mascota = serviceMascota.findById(idMascota).orElseThrow();
            vacuna.setMascota(mascota);
            serviceVacuna.save(vacuna);
            status.setComplete();

            // ✅ Nombre correcto + recargás los datos para que el form siga visible
            model.addAttribute("exitot", "💉 Vacunación registrada exitosamente");
            model.addAttribute("mostrarDatos", true);
            recargarModelo(propietarioid, model);
            return "clinico/nuevoVacunacion";

        } else {
            model.addAttribute("errort", "Error: propietario no encontrado");
            return "clinico/nuevoVacunacion";
        }
    }

    private void recargarModelo(Long propietarioid, Model model) {
        servicePropietario.findById(propietarioid).ifPresent(p -> {
            model.addAttribute("encontrado", p);
            model.addAttribute("mostrarDatos", true);
            model.addAttribute("mascotas", p.getMascotas());

            Map<Long, List<Vacunacion>> vacunasPorMascota = new HashMap<>();
            for (Mascota m : p.getMascotas()) {
                List<Vacunacion> vacunas = serviceVacuna.findByMascota(m);

                // ← agregá esto para debuggear
                System.out.println("Mascota ID: " + m.getId() + " | Vacunas encontradas: " + vacunas.size());

                vacunasPorMascota.put(m.getId(), vacunas);
            }

            System.out.println("Mapa completo: " + vacunasPorMascota.keySet());
            model.addAttribute("vacunasPorMascota", vacunasPorMascota);
        });
        model.addAttribute("vets", serviceVeterinario.findAll());
    }




}
