package com.planticasalquiler.demo.controllers;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.planticasalquiler.demo.models.Cliente;
import com.planticasalquiler.demo.models.Empleado;
import com.planticasalquiler.demo.models.Inventario;
import com.planticasalquiler.demo.models.Producto;
import com.planticasalquiler.demo.models.Rol;
import com.planticasalquiler.demo.repositories.ClienteRepository;
import com.planticasalquiler.demo.repositories.EmpleadoRepository;
import com.planticasalquiler.demo.repositories.InventarioRepository;
import com.planticasalquiler.demo.repositories.ProductoRepository;
import com.planticasalquiler.demo.repositories.RolRepository;

import jakarta.validation.Valid;



@Controller
public class AdminControllers {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private InventarioRepository inventarioRepository;

    //controllers empleado 
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

    @RequestMapping(value = "/createEmpleado", method = RequestMethod.POST)
public String guardar(@Valid Empleado empleado, BindingResult result, Model model, SessionStatus status) {
    if (result.hasErrors()) {
        model.addAttribute("titulo", "Formulario del Empleado");
        return "createEmpleado";
    }

    // Encripta la contraseña utilizando BCrypt
    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    String contraseñaEncriptada = bCryptPasswordEncoder.encode(empleado.getContrasena());
    empleado.setContrasena(contraseñaEncriptada);

    // Aquí puedes asignar los roles al empleado si lo deseas
    Set<Rol> roles = empleado.getRoles();
    // Procede a guardar el empleado
    empleadoRepository.save(empleado);
    status.setComplete();
    return "redirect:empleadosRegistrados";
}
//fin controllers empleado

//controllers clientes
    @GetMapping("/clientesRegistrados")
    public String clientesRegistrados(Model model) {
        List<Cliente> clientes = clienteRepository.finByClientes(0);
        model.addAttribute("clientes", clientes);
        
        return "listaClientes";
    }

    @GetMapping("/nuevoCliente")
    public String mostrarFormularioCliente(Model model) {
        model.addAttribute("cliente", new Cliente());
        return "formularioCliente";
    }

    @RequestMapping(value = "/createCliente", method = RequestMethod.POST)
    public String guardarCliente(@Valid Cliente cliente, BindingResult result, Model model, SessionStatus status) {
        if (result.hasErrors()) {
            model.addAttribute("titulo", "Formulario del Cliente");
            return "createCliente";
        }

        // Procede a guardar el empleado
        clienteRepository.save(cliente);
        status.setComplete();
        return "redirect:nuevaFactura";
    }
    //fin controllers clientes

    // controlles factura
    @RequestMapping("/nuevaFactura")
    public String filtro (Model model,@Param("dniFilter")String dniFilter){
        List<Cliente> clientes = clienteRepository.finByFilter(dniFilter);
        List<Producto> productos = productoRepository.finByProductos(0);
        model.addAttribute("productos", productos);
        model.addAttribute("clientes", clientes);
        model.addAttribute("dniFilter", dniFilter);
        
        return "formularioFactura";
    }
    // fin controllers factura

    // controllers productos
    @GetMapping("/productosRegistrados")
    public String productosRegistrados(Model model) {
        List<Producto> productos = productoRepository.finByProductos(0);
        model.addAttribute("productos", productos);
        return "listaProductos";
    }

    @GetMapping("/nuevoProducto")
    public String mostrarFormularioProducto(Model model) {
        model.addAttribute("producto", new Producto());
        return "formularioProducto";
    }

    @RequestMapping(value = "/createProducto", method = RequestMethod.POST)
    public String guardarProducto(@Valid Producto producto, BindingResult result, Model model, SessionStatus status) {
        if (result.hasErrors()) {
            model.addAttribute("titulo", "Formulario del Cliente");
            return "createProducto";
        }

        productoRepository.save(producto);
        status.setComplete();
        return "redirect:productosRegistrados";
    }
    
    //fin controllers productos


    // controllers inventario
    @GetMapping("/inventario")
    public String inventariu(Model model) {
        List<Inventario> inventarios = inventarioRepository.finByInventario(0);
        model.addAttribute("inventarios", inventarios);
        return "listaInventario";
    }

    @GetMapping("/nuevoInventario")
    public String mostrarFormularioInventario(Model model) {
        List<Producto> productos = productoRepository.finByProductos(0);
        model.addAttribute("inventario", new Inventario());
        model.addAttribute("productos", productos);
        return "formularioInventario";
    }

    @RequestMapping(value = "/createInventario", method = RequestMethod.POST)
    public String guardarInventario(@Valid Inventario inventario, BindingResult result, Model model, SessionStatus status) {
        if (result.hasErrors()) {
            model.addAttribute("titulo", "Formulario del Cliente");
            return "createProducto";
        }

        inventarioRepository.save(inventario);
        status.setComplete();
        return "redirect:productosRegistrados";
    }

    // fin controllers inventario
}
