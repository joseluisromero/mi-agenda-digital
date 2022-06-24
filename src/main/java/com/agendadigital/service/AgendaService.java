package com.agendadigital.service;

import com.agendadigital.dto.AgendaDto;
import com.agendadigital.dto.AgendaReporteDto;
import com.agendadigital.dto.ClienteDto;
import com.agendadigital.entity.Agenda;
import com.agendadigital.entity.Cliente;
import com.agendadigital.exception.ValidationServiceCustomer;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface AgendaService {
    AgendaDto save(AgendaDto agendaDto) throws ValidationServiceCustomer;

    AgendaDto update(AgendaDto agendaDto) throws ValidationServiceCustomer;

    List<AgendaDto> getAllAgendasUsername(String username);

    boolean delete(Integer id);

    List<AgendaReporteDto> getReporte(String username, Date init, Date end);

    List<AgendaReporteDto> getReporte2(String username, String titulo);

}
