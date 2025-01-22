package com.drvservicios.universidadFront.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.drvservicios.universidadFront.dtos.UserDTO;
import com.drvservicios.universidadFront.services.UserService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class LoginController {
    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login() {
        return "login"; 
    }

    @PostMapping("/signin")
    public String signin(UserDTO user, Model model) {
        String token = userService.signin(user);
        model.addAttribute("token", token);
        return "redirect:/"; // Redirige a la raíz
    }

}
