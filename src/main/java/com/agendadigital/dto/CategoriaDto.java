package com.agendadigital.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaDto implements Serializable {

    private Integer idCategoria;
    @NotBlank(message = "El nombre de la categoria es obligatorio")
    private String nombre;
}
