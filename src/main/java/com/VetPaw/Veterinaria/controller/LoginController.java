package com.VetPaw.Veterinaria.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class LoginController {

    @GetMapping("/login")
    public String login(){


        return "login";
    }
    @GetMapping("/")
    public String home() {
        return "login";
    }
}
