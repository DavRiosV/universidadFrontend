package com.drvservicios.universidadFront.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.drvservicios.universidadFront.services.AlumnoService;

@Controller
public class AlumnoController {
    @Autowired
    private AlumnoService alumnoService;

    @GetMapping("/alumnos")
    public String home(Model model) {
        model.addAttribute("alumnos", alumnoService.findAll());
        return "home"; // Asegúrate de que este archivo HTML exista y sea el adecuado
    }
}
