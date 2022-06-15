package com.agendadigital.repository;

import com.agendadigital.entity.Categoria;
import com.agendadigital.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

    Optional<Cliente> findByIdentificacion(@Param("identificacion") String identificacion);

    @Query("SELECT c FROM Cliente c WHERE (:identificacion is null or c.identificacion =: identificacion)")
    List<Cliente> getClienteByIdentificacion(@Param("identificacion") String identificacion);

    //security
    Optional<Cliente>findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

}
