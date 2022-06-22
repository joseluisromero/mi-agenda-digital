package com.agendadigital.dto;

import com.agendadigital.entity.AlertaAgenda;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AgendaDto implements Serializable {
    private Integer idAgenda;
    @NotBlank(message = "titulo es obligatorio")
    private String titulo;
    private String descripcion;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fechaCreacion;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fechaModificacion;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date fechaVencimiento;
    @Builder.Default
    private boolean notifica = false;
    @Builder.Default
    private Integer diasNotifica = 0;
    @Builder.Default
    private Integer estado = 1;
    private ClienteDto clienteDto;
    private CategoriaDto categoriaDto;
    private List<AlertaAgendaDto> alertaAgendaList = new ArrayList<>();
}
