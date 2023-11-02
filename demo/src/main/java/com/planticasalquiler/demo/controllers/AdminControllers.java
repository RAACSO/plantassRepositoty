package com.planticasalquiler.demo.controllers;

import java.util.List;
import java.util.Set;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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

    @RequestMapping(value = "/eliminarEmpleado/{id}")
    public String eliminarEmpleado(@PathVariable Long id) {
        empleadoRepository.deleteById(id); // Elimina el cliente por su ID
        return "redirect:/empleadosRegistrados"; // Redirige a la página de lista de clientes o a donde desees
    }

    @GetMapping("/editarEmpleado/{id}")
    public String mostrarFormularioEdicionEmpleado(@PathVariable Long id, Model model) {
        model.addAttribute("roles", rolRepository.findAll());
        Empleado empleado = empleadoRepository.findById(id).orElse(null);
        if (empleado != null) {
            model.addAttribute("empleado", empleado);
            return "formularioEditarEmpleado";
        } else {
            // Manejar el caso en el que el cliente no existe
            return "redirect:/empleadosRegistrados";
        }
    }

    @RequestMapping(value = "/editarEmpleado/{id}", method = RequestMethod.POST)
    public String procesarEdicionEmpleado(@PathVariable Long id, @ModelAttribute Empleado empleado) {
        Empleado empleadoExistente = empleadoRepository.findById(id).orElse(null);

        if (empleadoExistente != null) {
            // Actualizar los datos del cliente existente con los datos del formulario
            empleadoExistente.setNombre(empleado.getNombre());
            empleadoExistente.setTelefono(empleado.getTelefono());
            empleadoExistente.setRoles(empleado.getRoles());
            empleadoExistente.setUsuario(empleado.getUsuario());
            // Actualiza otros campos según sea necesario

            empleadoRepository.save(empleadoExistente);
        }

        return "redirect:/empleadosRegistrados";
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

    @RequestMapping(value = "/eliminarCliente/{id}")
    public String eliminarCliente(@PathVariable Long id) {
        clienteRepository.deleteById(id); // Elimina el cliente por su ID
        return "redirect:/clientesRegistrados"; // Redirige a la página de lista de clientes o a donde desees
    }

    @GetMapping("/editarCliente/{id}")
    public String mostrarFormularioEdicion(@PathVariable Long id, Model model) {
        Cliente cliente = clienteRepository.findById(id).orElse(null);

        if (cliente != null) {
            model.addAttribute("cliente", cliente);
            return "formularioEditarCliente";
        } else {
            // Manejar el caso en el que el cliente no existe
            return "redirect:/clientesRegistrados";
        }
    }

    @RequestMapping(value = "/editarCliente/{id}", method = RequestMethod.POST)
    public String procesarEdicionCliente(@PathVariable Long id, @ModelAttribute Cliente cliente) {
        Cliente clienteExistente = clienteRepository.findById(id).orElse(null);

        if (clienteExistente != null) {
            // Actualizar los datos del cliente existente con los datos del formulario
            clienteExistente.setNombre(cliente.getNombre());
            clienteExistente.setDireccion(cliente.getDireccion());
            clienteExistente.setTelefono(cliente.getTelefono());
            clienteExistente.setDni(cliente.getDni());
            // Actualiza otros campos según sea necesario

            clienteRepository.save(clienteExistente);
        }

        return "redirect:/clientesRegistrados";
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

    // @RequestMapping(value = "/eliminarProducto/{id}")
    // public String eliminarProducto(@PathVariable Long id) { 
    //     productoRepository.deleteById(id); // Elimina el cliente por su ID
    //     return "redirect:/productosRegistrados"; // Redirige a la página de lista de clientes o a donde desees
    // }

    @GetMapping("/editarProducto/{id}")
    public String mostrarFormularioEdicionProducto(@PathVariable Long id, Model model) {
        Producto producto = productoRepository.findById(id).orElse(null);
        if (producto != null) {
            model.addAttribute("producto", producto);
            return "formularioEditarProducto";
        } else {
            // Manejar el caso en el que el cliente no existe
            return "redirect:/productosRegistrados";
        }
    }

    @RequestMapping(value = "/editarProducto/{id}", method = RequestMethod.POST)
    public String procesarEdicionProdcuto(@PathVariable Long id, @ModelAttribute Producto producto) {
         Producto productoExistente = productoRepository.findById(id).orElse(null);

        if (productoExistente != null) {
            // Actualizar los datos del cliente existente con los datos del formulario
            productoExistente.setNombre(producto.getNombre());
            productoExistente.setPrecioDia(producto.getPrecioDia());
            // Actualiza otros campos según sea necesario

            productoRepository.save(productoExistente);
        }

        return "redirect:/productosRegistrados";
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
        return "redirect:inventario";
    }



    @GetMapping("/editarInventario/{id}")
    public String mostrarFormularioEdicionInventario(@PathVariable Long id, Model model) {
         List<Producto> productos = productoRepository.finByProductos(0);
        Inventario inventario = inventarioRepository.findById(id).orElse(null);
        model.addAttribute("productos", productos);
        if (inventario != null) {
            model.addAttribute("inventario", inventario);
            return "formularioEditarInventario";
        } else {
            // Manejar el caso en el que el cliente no existe
            return "redirect:/inventario";
        }
    }

    @RequestMapping(value = "/editarInventario/{id}", method = RequestMethod.POST)
    public String procesarEdicionInventario(@PathVariable Long id, @ModelAttribute Inventario inventario) {
        Inventario inventarioExistente = inventarioRepository.findById(id).orElse(null);

        if (inventarioExistente != null) {
            // Actualizar los datos del cliente existente con los datos del formulario
            inventarioExistente.setEstado(inventario.getEstado());
            // Actualiza otros campos según sea necesario

            inventarioRepository.save(inventarioExistente);
        }

        return "redirect:/inventario";
    }

    // fin controllers inventario
}
