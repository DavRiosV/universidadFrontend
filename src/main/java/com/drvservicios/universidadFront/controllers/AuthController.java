package com.drvservicios.universidadFront.controllers;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.drvservicios.universidadFront.services.BackendService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private BackendService backendService;

    @GetMapping("/login")
    public String loginPage() {
        logger.info("Cargando página de inicio de sesión.");
        return "login"; // Retorna la vista del formulario de login
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, Model model, HttpServletResponse response) {
        try {
            logger.info("Iniciando sesión para el usuario: {}", username);
            // Llama al backend para obtener el token
            Map<String, Object> backendResponse = backendService.login(username, password);
            String token = (String) backendResponse.get("token");

            if (token == null || token.isEmpty()) {
                logger.error("No se recibió un token válido del backend para el usuario: {}", username);
                model.addAttribute("error", "Error en la autenticación. Inténtelo nuevamente.");
                return "login";
            }

            // Crea la cookie con el token JWT
            Cookie jwtCookie = new Cookie("JWT_TOKEN", token);
            jwtCookie.setHttpOnly(true); // Asegura que la cookie no sea accesible por JavaScript
            jwtCookie.setPath("/"); // Disponible para toda la aplicación
            jwtCookie.setMaxAge(24 * 60 * 60); // Establece la cookie por 1 día
            response.addCookie(jwtCookie);

            logger.info("Usuario autenticado correctamente. Redirigiendo a /home.");
            return "redirect:/home"; // Redirige al usuario a la página principal
        } catch (Exception e) {
            logger.error("Error durante el inicio de sesión para el usuario: {}: {}", username, e.getMessage());
            // Manejo de errores: credenciales inválidas o problemas en el backend
            model.addAttribute("error", "Credenciales inválidas. Por favor, inténtelo de nuevo.");
            return "login";
        }
    }



    @GetMapping("/logout")
    public String logout(HttpServletResponse response) {
        logger.info("Cerrando sesión y eliminando cookie JWT.");
        // Opcional: Llama al backend para realizar logout si es necesario
        backendService.logout();

        // Elimina la cookie JWT
        Cookie cookie = new Cookie("JWT_TOKEN", null);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(0); // Expira inmediatamente para eliminarla
        response.addCookie(cookie);

        logger.info("Redirigiendo al formulario de inicio de sesión.");
        return "redirect:/auth/login"; // Redirige al formulario de login
    }
}
