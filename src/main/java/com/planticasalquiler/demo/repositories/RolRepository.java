package com.planticasalquiler.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.planticasalquiler.demo.models.Rol;

@Repository
public interface RolRepository  extends CrudRepository<Rol, Long>{

    @Query("SELECT r FROM Rol r WHERE r.id > :id")
    List<Rol> finByRoles(@Param("id") int id);
}
