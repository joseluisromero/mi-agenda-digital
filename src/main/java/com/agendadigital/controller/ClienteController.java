package com.agendadigital.controller;

import com.agendadigital.dto.ClienteDto;
import com.agendadigital.exception.ValidationServiceCustomer;
import com.agendadigital.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/cliente")
public class ClienteController {
    @Autowired
    private ClienteService clienteService;
    @PostMapping("/guardar")
    public ResponseEntity<ClienteDto> save(@RequestBody @Valid ClienteDto clienteDto) throws ValidationServiceCustomer {
        try {
            ClienteDto clienteDto1 = clienteService.save(clienteDto);
            return new ResponseEntity<ClienteDto>(clienteDto1, HttpStatus.CREATED);
        } catch (DataAccessException e) {
            return new ResponseEntity("Error: " + e.getMostSpecificCause().getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/todos")
    public ResponseEntity<List<?>> getClientes(@RequestParam(name = "identificacion",required = false) String identificacion) {

        try {
            List<ClienteDto> clienteDtoList = clienteService.getClientes(identificacion);
            return new ResponseEntity<>(clienteDtoList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity("Error al obtener la información" + e.getCause().getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
