package com.agendadigital.controller;

import com.agendadigital.dto.AlertaAgendaDto;
import com.agendadigital.exception.ValidationServiceCustomer;
import com.agendadigital.service.AlertaAgendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/alerta")
public class AlertaAgendaController {
    @Autowired
    private AlertaAgendaService alertaAgendaService;

    @PostMapping("/guardar")
    public ResponseEntity<AlertaAgendaDto> save(@RequestBody @Valid AlertaAgendaDto alertaAgendaDto) throws ValidationServiceCustomer {
        try {
            AlertaAgendaDto categoriaDto1 = alertaAgendaService.save(alertaAgendaDto);
            return new ResponseEntity<AlertaAgendaDto>(categoriaDto1, HttpStatus.CREATED);
        } catch (DataAccessException e) {
            return new ResponseEntity("Error: " + e.getMostSpecificCause().getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/actualizar")
    public ResponseEntity<AlertaAgendaDto> update(@RequestBody @Valid AlertaAgendaDto alertaAgendaDto) throws ValidationServiceCustomer {
        try {
            AlertaAgendaDto categoriaDto1 = alertaAgendaService.update(alertaAgendaDto);
            return new ResponseEntity<AlertaAgendaDto>(categoriaDto1, HttpStatus.OK);
        } catch (DataAccessException e) {
            return new ResponseEntity("Error: " + e.getMostSpecificCause().getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/todos")
    public ResponseEntity<List<?>> getAlertaAgenda(@RequestParam(name = "idAgenda",required = false) Integer idAgenda) {

        try {
            List<AlertaAgendaDto> categoriaDtoList = alertaAgendaService.getAlertaAgendaByAgenda(idAgenda);
            return new ResponseEntity<>(categoriaDtoList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity("Error al obtener la información" + e.getCause().getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
