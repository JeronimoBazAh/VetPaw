package com.VetPaw.Veterinaria.controller;

import com.VetPaw.Veterinaria.model.Usuario;
import com.VetPaw.Veterinaria.model.Veterinario;
import com.VetPaw.Veterinaria.service.UserService;
import com.VetPaw.Veterinaria.service.VeterinarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/auth")
public class loginController {

    @Autowired
    private UserService usuarioService;

    @Autowired
    private VeterinarioService vetService;




    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false)String error, @RequestParam(value = "logout",required = false)String logout, Model model){
        if(error != null){
            model.addAttribute("error", "Usuario o contraseña incorrectos");

        }
        if(logout != null){
            model.addAttribute("message", "sesion cerrada");

        }


        return "login";
    }


    @GetMapping("/")
    public String home() {
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model){
        Usuario user = new Usuario();
        Veterinario vet = new Veterinario();
        model.addAttribute("user",user);
        model.addAttribute("vet",vet);

        return "register";
    }

    @PostMapping("/register")
    public String form(@Valid @ModelAttribute("user") Usuario user,@Valid @ModelAttribute("user") Veterinario vet, RedirectAttributes redirectAttributes,BindingResult result,String rol, Model model, RedirectAttributes redirect, SessionStatus status){

        try{
            if(rol == "Veterinario"){
                vetService.registrarUsuario(vet);

            } else if (rol ==  "Recepcionista") {
                usuarioService.registrarUsuario(user);
                redirectAttributes.addFlashAttribute("success", "Registro exitoso");
                return "redirect:/auth/login";
            }

        }catch(Exception e){
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/auth/register";
        }

        return "redirect:/auth/login";

    }
    @GetMapping("/register-veterinario")
    public String registerVet(Model model){

        model.addAttribute("vet", new Veterinario());

        return "registerVet";
    }

    @GetMapping("/postRegistro")
    public String post(){


        return "postRegistro";
    }

    @GetMapping("/vet")
    public String principalVet(){


        return "/clinico/principalVet";
    }

    @GetMapping("/inicio")
    public String inicio(){


        return "inicio";
    }

}
