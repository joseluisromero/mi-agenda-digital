package com.agendadigital.service;

import com.agendadigital.dto.CategoriaDto;
import com.agendadigital.dto.ClienteDto;
import com.agendadigital.exception.ValidationServiceCustomer;

import java.util.List;

public interface ClienteService {
    ClienteDto save(ClienteDto clienteDto) throws ValidationServiceCustomer;
    List<ClienteDto> getClientes(String identificacion);
}
