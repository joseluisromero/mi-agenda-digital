package com.agendadigital.service;

import com.agendadigital.dto.RolesDto;

import java.util.List;
import java.util.Optional;

public interface RolesService {
    Optional<RolesDto> findByNombre(String nombre);

    RolesDto save(RolesDto rolesDto);

    List<RolesDto> getRoles();
}
