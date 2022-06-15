package com.agendadigital.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@Builder
public class RolesDto {
    private Integer idRoles;
    @NotBlank(message = "El nombre del rol es obligatorio")
    private String nombre;
}