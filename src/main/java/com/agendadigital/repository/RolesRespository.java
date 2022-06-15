package com.agendadigital.repository;

import com.agendadigital.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolesRespository extends JpaRepository<Roles, Integer> {
    Optional<Roles>findByNombre(String nombre);
}
