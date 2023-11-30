package com.planticasalquiler.demo.repositories;

import org.springframework.data.repository.CrudRepository;

import com.planticasalquiler.demo.models.Inventario;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface InventarioRepository extends CrudRepository<Inventario, Long>{
     @Query("SELECT i FROM Inventario i WHERE i.id > :id")
    List<Inventario> finByInventario(@Param("id") int id);
}
