package com.VetPaw.Veterinaria.controller;

import com.VetPaw.Veterinaria.model.Usuario;
import com.VetPaw.Veterinaria.service.Service;
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
    private final Service<Usuario> service;


    public LoginController(Service<Usuario> service) {
        this.service = service;
    }

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
    public String register(){


        return "register";
    }

    @PostMapping("/register")
    public String form(@Valid @ModelAttribute("user") Usuario user, BindingResult result, Model model, RedirectAttributes redirect, SessionStatus status){

        if(result.hasErrors()){
            model.addAttribute("title", "Validando formulario");
            return "form";
        }else{
            service.save(user);
            return "redirect:/auth/postRegistro";
        }


    }

    @GetMapping("/postRegistro")
    public String post(){


        return "postRegistro";
    }

}
