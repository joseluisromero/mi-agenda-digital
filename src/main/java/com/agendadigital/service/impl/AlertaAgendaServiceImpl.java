package com.agendadigital.service.impl;

import com.agendadigital.dto.AgendaDto;
import com.agendadigital.dto.AlertaAgendaDto;
import com.agendadigital.repository.AlertaAgendaServiceRepository;
import com.agendadigital.service.AlertaAgendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AlertaAgendaServiceImpl implements AlertaAgendaService {
    @Autowired
    AlertaAgendaServiceRepository alertaAgendaServiceRepository;

    @Override
    public AlertaAgendaDto save(AlertaAgendaDto alertaAgendaDto) {
        return null;
    }

    @Override
    public AlertaAgendaDto update(AlertaAgendaDto alertaAgendaDto) {
        return null;
    }

    @Override
    public boolean eliminar(Integer idAlerta) {
        return false;
    }

    @Override
    public List<AlertaAgendaDto> getAlertaAgendaByAgenda(Integer idAgenda) {
        return null;
    }
}
