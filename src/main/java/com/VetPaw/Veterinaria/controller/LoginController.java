package com.VetPaw.Veterinaria.controller;

import com.VetPaw.Veterinaria.model.Usuario;
import com.VetPaw.Veterinaria.service.Service;
import com.VetPaw.Veterinaria.service.UserService;
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
public class LoginController {

    @Autowired
    private UserService usuarioService;



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
        model.addAttribute("user",user);

        return "register";
    }

    @PostMapping("/register")
    public String form(@Valid @ModelAttribute("user") Usuario user, RedirectAttributes redirectAttributes,BindingResult result, Model model, RedirectAttributes redirect, SessionStatus status){

        try{
            usuarioService.registrarUsuario(user);
            redirectAttributes.addFlashAttribute("success", "Registro exitoso");
            return "redirect:/auth/login";
        }catch(Exception e){
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/auth/register";
        }


    }

    @GetMapping("/postRegistro")
    public String post(){


        return "postRegistro";
    }

    @GetMapping("/vet")
    public String principalVet(){


        return "/clinico/principalVet";
    }

}
