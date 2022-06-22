package com.agendadigital.dto;

import com.agendadigital.entity.Agenda;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AlertaAgendaDto implements Serializable {
    private Integer idAlerta;
    @NotBlank(message = "El campo nombre {message.requerido}")
    private String nombre;
    private LocalDateTime fechaNotifica;
    private AgendaDto agendaDto;
}
