package com.VetPaw.Veterinaria.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/auth")
public class loginController {

    @GetMapping("/login")
    public String login(){


        return "login.html";
    }
}
