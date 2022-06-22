package com.agendadigital.service;

import com.agendadigital.dto.AgendaDto;
import com.agendadigital.dto.AlertaAgendaDto;

import java.util.List;

public interface AlertaAgendaService {
    AlertaAgendaDto save(AlertaAgendaDto alertaAgendaDto);

    AlertaAgendaDto update(AlertaAgendaDto alertaAgendaDto);

    boolean eliminar(Integer idAlerta);

    List<AlertaAgendaDto> getAlertaAgendaByAgenda(Integer idAgenda);
}
