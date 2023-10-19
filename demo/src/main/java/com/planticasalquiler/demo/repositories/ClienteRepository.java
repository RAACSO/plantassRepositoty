package com.planticasalquiler.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.planticasalquiler.demo.models.Cliente;

@Repository
public interface ClienteRepository  extends CrudRepository<Cliente, Long>{
    @Query("SELECT c FROM Cliente c WHERE c.id > :id")
    List<Cliente> finByClientes(@Param("id") int id);
}
