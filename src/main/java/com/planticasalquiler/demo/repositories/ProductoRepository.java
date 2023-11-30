package com.planticasalquiler.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.planticasalquiler.demo.models.Producto;

@Repository
public interface ProductoRepository extends CrudRepository<Producto, Long>{
    @Query("SELECT p FROM Producto p WHERE p.id > :id")
    List<Producto> finByProductos(@Param("id") int id);
}
