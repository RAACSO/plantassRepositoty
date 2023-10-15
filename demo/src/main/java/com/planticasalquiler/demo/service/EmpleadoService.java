package com.planticasalquiler.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.planticasalquiler.demo.models.Empleado;
import com.planticasalquiler.demo.repositories.EmpleadoRepository;
import com.planticasalquiler.demo.service.impl.IEmpleadoService;

public class EmpleadoService implements IEmpleadoService{
    private final EmpleadoRepository empleadoRepository;

    @Autowired
    public EmpleadoService(EmpleadoRepository empleadoRepository) {
        this.empleadoRepository = empleadoRepository;
    }

    @Override
    @Transactional
    public void save(Empleado empleado){
        empleadoRepository.save(empleado);
    }
}
