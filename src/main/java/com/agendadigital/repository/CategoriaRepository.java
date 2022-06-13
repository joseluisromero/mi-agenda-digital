package com.agendadigital.repository;

import com.agendadigital.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {

    Optional<Categoria> findByNombre(@Param("nombre") String nombre);

    @Query("SELECT c FROM Categoria c WHERE (:nombre is null or c.nombre =: nombre)")
    List<Categoria> getCategoriaByNombre(@Param("nombre") String nombre);
}
