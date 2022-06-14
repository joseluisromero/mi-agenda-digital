package com.agendadigital.service.impl;

import com.agendadigital.config.Config;
import com.agendadigital.dto.ClienteDto;
import com.agendadigital.entity.Cliente;
import com.agendadigital.exception.ValidationServiceCustomer;
import com.agendadigital.repository.ClienteRepository;
import com.agendadigital.service.ClienteService;
import com.agendadigital.util.ConversionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private ConversionUtil conversionUtil;

    @Autowired
    private Config config;


    @Transactional
    @Override
    public ClienteDto save(ClienteDto clienteDto) throws ValidationServiceCustomer {
        Optional<Cliente> clienteOptional = clienteRepository.findByIdentificacion(clienteDto.getIdentificacion());
        if (clienteOptional.isPresent()) {
            throw new ValidationServiceCustomer("El cliente " + clienteDto.getIdentificacion() + " ya existe", HttpStatus.PRECONDITION_FAILED);
        }
        Cliente cliente = conversionUtil.buildClienteDtoToCliente(clienteDto);
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        cliente.setPassword(encoder.encode(cliente.getPassword()));
        cliente = clienteRepository.save(cliente);
        return conversionUtil.buildClienteToClienteDto(cliente);
    }

    @Override
    public List<ClienteDto> getClientes(String identificacion) {
        List<ClienteDto> clienteDtoList = new ArrayList<>();
        List<Cliente> clientes = clienteRepository.getClienteByIdentificacion(identificacion == null ? null : identificacion);
        clientes.stream().forEach(cliente -> {
            clienteDtoList.add(conversionUtil.buildClienteToClienteDto(cliente));
        });

        return clienteDtoList;
    }
}
