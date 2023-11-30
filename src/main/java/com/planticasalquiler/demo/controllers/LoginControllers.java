package com.planticasalquiler.demo.controllers;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;

@Controller
public class LoginControllers {
    
    @GetMapping("/index")
    public String index(){
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login"; // El nombre de la vista HTML
    }

      @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        // Realiza las tareas necesarias antes de cerrar la sesión, si las hay.
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?logout"; // Redirige a la página de inicio de sesión u otra página de tu elección.
    }
}
