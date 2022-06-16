package com.agendadigital.service.impl;

import com.agendadigital.dto.ClienteDto;
import com.agendadigital.entity.Cliente;
import com.agendadigital.entity.Roles;
import com.agendadigital.exception.ValidationServiceCustomer;
import com.agendadigital.repository.ClienteRepository;
import com.agendadigital.repository.RolesRespository;
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
import java.util.stream.Collectors;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private ConversionUtil conversionUtil;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private RolesRespository rolesRespository;

    @Transactional
    @Override
    public ClienteDto save(ClienteDto clienteDto) throws ValidationServiceCustomer {
        Optional<Cliente> clienteOptional = clienteRepository.findByIdentificacionOrUsernameOrEmail(clienteDto.getIdentificacion(), clienteDto.getUsername(), clienteDto.getEmail());
        if (clienteOptional.isPresent()) {
            throw new ValidationServiceCustomer("El cliente identificacion: " + clienteDto.getIdentificacion() + " con username: " + clienteDto.getUsername() + " y email: " + clienteDto.getEmail() + " ya existe", HttpStatus.PRECONDITION_FAILED);
        }
        Cliente cliente = conversionUtil.buildClienteDtoToCliente(clienteDto);
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        cliente.setPassword(encoder.encode(cliente.getPassword()));

        Set<Roles> roles = new HashSet<>();
        Optional<Roles>rolesUser=rolesRespository.findByNombre(RolType.ROLE_USER.name());
        if(rolesUser.isPresent())
        roles.add(rolesUser.get());
        else
            throw new ValidationServiceCustomer("El "+RolType.ROLE_USER.name()+" no se encuentra registrado",HttpStatus.PRECONDITION_FAILED);
        if (clienteDto.getRoles().contains("admin")) {
            Optional<Roles> rolesAdmin = rolesRespository.findByNombre(RolType.ROLE_ADMIN.name());
            if(rolesAdmin.isPresent())
                roles.add(rolesAdmin.get());
            else
                throw new ValidationServiceCustomer("El "+RolType.ROLE_ADMIN.name()+" no se encuentra registrado",HttpStatus.PRECONDITION_FAILED);

        }
        cliente.setRoles(roles);
        cliente = clienteRepository.save(cliente);
        return conversionUtil.buildClienteToClienteDto(cliente);
    }

    @Transactional
    @Override
    public ClienteDto update(ClienteDto clienteDto) throws ValidationServiceCustomer {
        if (clienteDto.getIdCliente() == null)
            throw new ValidationServiceCustomer("EL id del cliente debe estar presente", HttpStatus.BAD_REQUEST);
        Optional<Cliente> clienteOptional = clienteRepository.findById(clienteDto.getIdCliente());
        if (!clienteOptional.isPresent()) {
            throw new ValidationServiceCustomer("El cliente identificacion: " + clienteDto.getIdentificacion() + " con username: " + clienteDto.getUsername() + " y email: " + clienteDto.getEmail() + " no se encuentra registrado", HttpStatus.PRECONDITION_FAILED);
        }
        Cliente cliente = modelMapper.map(clienteDto, Cliente.class);
        if (validateClienteUpdate(clienteOptional, clienteDto)) {
            Set<Roles> roles = new HashSet<>();
            clienteOptional.get().getRoles().stream().forEach(roles1 -> {
                roles.add(rolesRespository.findByNombre(roles1.getNombre()).get());
            });

            if (clienteDto.getRoles().contains("ROLE_ADMIN") && !roles.stream().map(Roles::getNombre).collect(Collectors.toList()).contains("ROLE_ADMIN"))
                roles.add(rolesRespository.findByNombre(RolType.ROLE_ADMIN.name()).get());

            cliente.setRoles(roles);

            cliente = clienteRepository.save(cliente);
        }
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

    private boolean validateClienteUpdate(Optional<Cliente> clienteDb, ClienteDto clienteDto) {
        if (clienteDb.get().getIdentificacion().equals(clienteDto.getIdentificacion())
                && clienteDb.get().getUsername().equals(clienteDto.getUsername())
                && clienteDb.get().getEmail().equals(clienteDto.getEmail()))
            return true;
        else {
            Optional<Cliente> clienteIdentificacion = clienteRepository.findByIdentificacion(clienteDto.getIdentificacion());
            if (clienteIdentificacion.isPresent()
                    && clienteIdentificacion.get().getIdCliente() != clienteDb.get().getIdCliente())
                throw new ValidationServiceCustomer("El cliente con la identificacion " + clienteDto.getIdentificacion() + " ya se encuentra registrada", HttpStatus.PRECONDITION_FAILED);

            Optional<Cliente> clienteUsername = clienteRepository.findByUsername(clienteDto.getUsername());
            if (clienteUsername.isPresent()
                    && clienteUsername.get().getIdCliente() != clienteDb.get().getIdCliente())
                throw new ValidationServiceCustomer("El cliente con la username " + clienteDto.getUsername() + " ya se encuentra registrada", HttpStatus.PRECONDITION_FAILED);

            Optional<Cliente> clienteEmail = clienteRepository.findByEmail(clienteDto.getEmail());
            if (clienteEmail.isPresent()
                    && clienteEmail.get().getIdCliente() != clienteDb.get().getIdCliente())
                throw new ValidationServiceCustomer("El cliente con email " + clienteDto.getEmail() + " ya se encuentra registrada", HttpStatus.PRECONDITION_FAILED);

            return true;
        }
    }
}
