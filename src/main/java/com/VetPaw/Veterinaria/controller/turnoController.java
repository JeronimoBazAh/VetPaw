package com.VetPaw.Veterinaria.controller;

import com.VetPaw.Veterinaria.model.Mascota;
import com.VetPaw.Veterinaria.model.Turno;
import com.VetPaw.Veterinaria.model.Usuario;
import com.VetPaw.Veterinaria.model.Veterinario;
import com.VetPaw.Veterinaria.service.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/administracion")
public class turnoController {

    @Autowired
    VeterinarioService vetService;

    @Autowired
    TurnoService turnoService;

    @Autowired
    PropietarioService propietarioService;

    @Autowired
    UserService usuarioService;

    @Autowired
    MascotaService mascotaService;

    @GetMapping("/turnos")
    public String turnos(){


        List<Turno> listarTurnos;
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

    @PostMapping("/crear")
    public String crearTurno(@Valid @ModelAttribute("turno") Turno turno,
                             BindingResult result,
                             @RequestParam("idMascota")     Long idMascota,
                             @RequestParam("idVeterinario") Long idVeterinario,
                             @RequestParam("recep")         Long idRecep,
                             @RequestParam("propietarioid") Long propietarioId,
                             @RequestParam("hora")          String horaStr,
                             Model model,
                             RedirectAttributes redirect,
                             SessionStatus status) {

        System.out.println("=== idVeterinario recibido: " + idVeterinario);
        System.out.println("=== IDs vets en BD: " + vetService.findAll().stream().map(v -> v.getId()).toList());
        // 1. Errores de validación del objeto Turno
        if (result.hasErrors()) {

            recargarModel(model, propietarioId);
            model.addAttribute("errort", "Corregí los campos marcados antes de continuar.");
            model.addAttribute("vets", vetService.findAll());
            System.out.println("=== idVeterinario recibido: " + idVeterinario);
            System.out.println("=== Vets en BD: " + vetService.findAll().stream().map(v -> v.getId()).toList());

            return "turnos/nuevoTurno";
        }

        // 2. Verificar que la mascota exista
        Optional<Mascota> mascotaOpt = mascotaService.findById(idMascota);
        if (mascotaOpt.isEmpty()) {
            recargarModel(model, propietarioId);
            model.addAttribute("errort", "La mascota seleccionada no existe.");
            return "turnos/nuevoTurno";
        }

        // 3. Verificar que el veterinario exista
        Optional<Veterinario> vetOpt = vetService.findById(idVeterinario);
        if (vetOpt.isEmpty()) {
            recargarModel(model, propietarioId);
            model.addAttribute("errort", "El veterinario seleccionado no existe.");
            return "turnos/nuevoTurno";
        }

        // 4. Verificar que el recepcionista exista
        Optional<Usuario> recepOpt = usuarioService.findById(idRecep);
        if (recepOpt.isEmpty()) {
            recargarModel(model, propietarioId);
            model.addAttribute("errort", "El recepcionista seleccionado no existe.");
            return "turnos/nuevoTurno";
        }

        // 5. Convertir la hora recibida del input type="time" (formato HH:mm)
        //    combinándola con la fecha del turno para armar un LocalDateTime
        //    que mapea correctamente a la columna datetime de MySQL
        LocalDateTime hora;
        try {
            LocalTime localTime = LocalTime.parse(horaStr);
            hora = LocalDateTime.of(turno.getFecha(), localTime);
        } catch (Exception e) {
            recargarModel(model, propietarioId);
            model.addAttribute("errort", "El formato de hora es inválido. Usá HH:mm.");
            return "turnos/nuevoTurno";
        }

        // 6. Verificar que no haya un turno solapado para ese veterinario
        //    en la misma fecha y hora exacta
        boolean turnoExistente = turnoService.existeTurnoSolapado(vetOpt.get(), turno.getFecha(), hora);
        if (turnoExistente) {
            recargarModel(model, propietarioId);
            model.addAttribute("errort", "El veterinario ya tiene un turno registrado en esa fecha y hora. Por favor, seleccioná otro horario.");
            return "turnos/nuevoTurno";
        }

        // 7. Armar el turno completo y guardar
        turno.setMascota(mascotaOpt.get());
        turno.setVet(vetOpt.get());
        turno.setRecep(recepOpt.get());
        turno.setHora(hora);
        turno.setFechaSolicitud(LocalDate.now());
        turno.setEstado("PROGRAMADO");

        turnoService.save(turno);
        status.setComplete();

        redirect.addFlashAttribute("exitot", "Turno registrado correctamente para "
                + mascotaOpt.get().getNombre() + " el " + turno.getFecha()
                + " a las " + horaStr);

        return "redirect:/administracion/crearTurno";
    }

    @InitBinder("turno")
    protected void initBinder(WebDataBinder binder) {
        binder.setDisallowedFields("hora", "recep", "vet", "mascota", "fechaSolicitud");
    }
/*
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
            System.out.println("no guardo un pingo ");
            return "clinico/registrarMascota";
        }


    }

 */

    private void recargarModel(Model model, Long propietarioId) {
        model.addAttribute("vets", vetService.findAll());
        model.addAttribute("mostrarDatos", true);
        model.addAttribute("encontrado", propietarioService.findById(propietarioId).orElse(null));
    }


}
