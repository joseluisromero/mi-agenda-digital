package com.agendadigital.repository;

import com.agendadigital.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

    Optional<Cliente> findByIdentificacionOrUsernameOrEmail(@Param("identificacion") String identificacion, @Param("username") String username, @Param("email") String email);

    @Query("SELECT c FROM Cliente c WHERE (:identificacion is null or c.identificacion =: identificacion)")
    List<Cliente> getClienteByIdentificacion(@Param("identificacion") String identificacion);

    Optional<Cliente> findByUsername(String username);

    Optional<Cliente> findByEmail(String email);

    Optional<Cliente> findByIdentificacion(String identificacion);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

}
