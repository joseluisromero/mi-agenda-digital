package com.agendadigital.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@Builder
public class ClienteDto implements Serializable {

    private Integer idCliente;
    @NotBlank(message = "La identificacion es obligatorio")
    private String identificacion;
    @NotBlank(message = "El nombre es obligatorio")
    private String nombres;
    @NotBlank(message = "El apellido es obligatorio")
    private String apellidos;
    @NotBlank(message = "El email es obligatorio")
    private String email;
    @NotBlank(message = "El username es obligatorio")
    private String username;
    @NotBlank(message = "El password es obligatorio")
    private String password;
    private int estado;
}
