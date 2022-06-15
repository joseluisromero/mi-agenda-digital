package com.agendadigital.service.impl;

import com.agendadigital.dto.RolesDto;
import com.agendadigital.entity.Roles;
import com.agendadigital.exception.ValidationServiceCustomer;
import com.agendadigital.repository.RolesRespository;
import com.agendadigital.security.enums.RolType;
import com.agendadigital.service.RolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RolesServiceImpl implements RolesService {

    @Autowired
    private RolesRespository rolesRespository;

    @Override
    public Optional<RolesDto> findByNombre(String nombre) {
        Optional<Roles> roles = rolesRespository.findByNombre(nombre);
        if (roles.isPresent())
            return Optional.of(RolesDto.builder().idRoles(roles.get().getIdRoles()).nombre(roles.get().getNombre()).build());
        else
            return Optional.empty();
    }

    @Override
    public RolesDto save(RolesDto rolesDto) {
        Optional<Roles> rolesOptional = rolesRespository.findByNombre(rolesDto.getNombre());
        if (rolesOptional.isPresent())
            throw new ValidationServiceCustomer("El rol " + rolesDto.getNombre() + " ya  existe", HttpStatus.PRECONDITION_FAILED);
        Roles roles = Roles.builder().nombre(rolesDto.getNombre().contains("USER")? RolType.ROLE_USER.name():RolType.ROLE_ADMIN.name()).build();
        roles = rolesRespository.save(roles);
        return RolesDto.builder().idRoles(roles.getIdRoles())
                .nombre(roles.getNombre())
                .build();

    }

    @Override
    public List<RolesDto> getRoles() {
        List<Roles> roles = rolesRespository.findAll();
        List<RolesDto> rolesDtos = new ArrayList<>();
        rolesDtos = roles.stream().map(rol -> {
            RolesDto rolesDto = RolesDto.builder().idRoles(rol.getIdRoles())
                    .nombre(rol.getNombre())
                    .build();
            return rolesDto;
        }).collect(Collectors.toList());
        return rolesDtos;
    }

}
