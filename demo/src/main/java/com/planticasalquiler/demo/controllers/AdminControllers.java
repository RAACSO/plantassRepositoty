package com.planticasalquiler.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;

import com.planticasalquiler.demo.models.Empleado;
import com.planticasalquiler.demo.models.Rol;
import com.planticasalquiler.demo.repositories.EmpleadoRepository;
import com.planticasalquiler.demo.repositories.RolRepository;

@Controller
public class AdminControllers {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Autowired
    private RolRepository rolRepository;

    @GetMapping("/empleadosRegistrados")
    public String empleadosRegistrados(Model model) {
        List<Empleado> empleados = empleadoRepository.finByEmpleados(0);
        model.addAttribute("empleados", empleados);
        
        return "listaEmpleado";
    }

    @GetMapping("/nuevoEmpleado")
    public String mostrarFormulario(Model model) {
        model.addAttribute("empleado", new Empleado());
        model.addAttribute("roles", rolRepository.findAll());
        return "formularioEmpleado";
    }

   

    @PostMapping("/crearEmpleado")
    public String crearEmpleado(@ModelAttribute("empleado") Empleado empleado, Model model) {
        // Cifrar la contraseña antes de guardarla en la base de datos
        empleado.setCifrarContrasena(empleado.getContrasena());
        empleadoRepository.save(empleado);
        return "redirect:/listaEmpleado";
    }
}
