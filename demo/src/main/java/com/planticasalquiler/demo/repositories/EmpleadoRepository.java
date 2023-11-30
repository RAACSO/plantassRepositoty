package com.planticasalquiler.demo.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.planticasalquiler.demo.models.Empleado;

@Repository
public interface EmpleadoRepository extends CrudRepository<Empleado, Long>{

    @Query("select u from Empleado u where u.usuario = ?1")
    Optional<Empleado> getName(String username);

    @Query("SELECT p FROM Empleado p WHERE p.id > :id")
    List<Empleado> finByEmpleados(@Param("id") int id);

}
