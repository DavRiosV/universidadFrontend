package com.drvservicios.universidadFront.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.drvservicios.universidadFront.services.BackendService;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private BackendService backendService;

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, Model model) {
        try {
            var response = backendService.login(username, password);
            String token = (String) response.get("token");
            model.addAttribute("token", token); // Se puede usar si es necesario
            return "redirect:/home";
        } catch (Exception e) {
            model.addAttribute("error", "Credenciales inválidas. Por favor, inténtelo de nuevo.");
            return "login";
        }
    }

    @GetMapping("/signup")
    public String signupPage() {
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(@RequestParam Map<String, Object> userDetails, Model model) {
        try {
            backendService.signup(userDetails);
            return "redirect:/auth/login";
        } catch (Exception e) {
            model.addAttribute("error", "Error al registrar usuario.");
            return "signup";
        }
    }
}
