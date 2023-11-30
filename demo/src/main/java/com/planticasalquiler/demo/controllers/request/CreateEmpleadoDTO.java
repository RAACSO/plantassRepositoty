package com.planticasalquiler.demo.controllers.request;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateEmpleadoDTO {

    
    private String nombre;

    
    private String usuario;

    
    private String contrasena;

    
    private String telefono;

    private Set<String> roles;
}
