package com.agendadigital.service;

import com.agendadigital.dto.CategoriaDto;
import com.agendadigital.dto.ClienteDto;
import com.agendadigital.entity.Cliente;
import com.agendadigital.exception.ValidationServiceCustomer;

import java.util.List;
import java.util.Optional;

public interface ClienteService {
    ClienteDto save(ClienteDto clienteDto) throws ValidationServiceCustomer;

    List<ClienteDto> getClientes(String identificacion);

     Optional<Cliente> getByUsername(String username);

     boolean existsByUsername(String username);


     boolean existsByEmail(String email);
}
