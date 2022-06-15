package com.agendadigital.service.impl;

import com.agendadigital.dto.ClienteDto;
import com.agendadigital.entity.Cliente;
import com.agendadigital.entity.Roles;
import com.agendadigital.exception.ValidationServiceCustomer;
import com.agendadigital.repository.ClienteRepository;
import com.agendadigital.repository.RolesRespository;
import com.agendadigital.security.MainSecurity;
import com.agendadigital.security.enums.RolType;
import com.agendadigital.service.ClienteService;
import com.agendadigital.util.ConversionUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private ConversionUtil conversionUtil;

    @Autowired
    private ModelMapper modelMapper;

    //@Autowired
    //private MainSecurity mainSecurity;

    @Autowired
    private RolesRespository rolesRespository;


    @Transactional
    @Override
    public ClienteDto save(ClienteDto clienteDto) throws ValidationServiceCustomer {
        Optional<Cliente> clienteOptional = clienteRepository.findByIdentificacion(clienteDto.getIdentificacion());
        if (clienteOptional.isPresent()) {
            throw new ValidationServiceCustomer("El cliente " + clienteDto.getIdentificacion() + " ya existe", HttpStatus.PRECONDITION_FAILED);
        }
        Optional<Cliente> clienteOptional2 = clienteRepository.findByUsername(clienteDto.getUsername());
        if (clienteOptional2.isPresent()) {
            throw new ValidationServiceCustomer("El usuario " + clienteDto.getUsername() + " ya existe", HttpStatus.PRECONDITION_FAILED);
        }
        Cliente cliente = conversionUtil.buildClienteDtoToCliente(clienteDto);
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        cliente.setPassword(encoder.encode(cliente.getPassword()));

        Set<Roles> roles = new HashSet<>();
        roles.add(rolesRespository.findByNombre(RolType.ROLE_USER.name()).get());
        if (clienteDto.getRoles().contains("admin"))
            roles.add(rolesRespository.findByNombre(RolType.ROLE_ADMIN.name()).get());

        cliente.setRoles(roles);

        cliente = clienteRepository.save(cliente);
        return conversionUtil.buildClienteToClienteDto(cliente);
    }

    @Override
    public List<ClienteDto> getClientes(String identificacion) {
        List<ClienteDto> clienteDtoList = new ArrayList<>();
        List<Cliente> clientes = clienteRepository.getClienteByIdentificacion(identificacion == null ? null : identificacion);
        clientes.stream().forEach(cliente -> {
            //clienteDtoList.add(modelMapper.map(cliente, ClienteDto.class));
            clienteDtoList.add(conversionUtil.buildClienteToClienteDto(cliente));
        });

        return clienteDtoList;
    }

    //security
    @Override
    public Optional<Cliente> getByUsername(String username) {
        return clienteRepository.findByUsername(username);
    }

    @Override
    public boolean existsByUsername(String username) {
        return clienteRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return clienteRepository.existsByEmail(email);
    }
}
