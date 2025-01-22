package com.drvservicios.universidadFront.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String homeRedirect() {
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("message", "Bienvenido al Sistema de Calificaciones");
        System.out.print("Aqui estoy!!!!!");
        return "home";}
}
