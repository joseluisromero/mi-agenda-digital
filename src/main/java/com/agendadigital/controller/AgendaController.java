package com.agendadigital.controller;

import com.agendadigital.dto.AgendaDto;
import com.agendadigital.dto.AgendaReporteDto;
import com.agendadigital.exception.ValidationServiceCustomer;
import com.agendadigital.service.AgendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/agenda")
public class AgendaController {
    @Autowired
    private AgendaService agendaService;

    @PostMapping("/guardar")
    public ResponseEntity<?> save(@RequestBody @Valid AgendaDto agendaDto, BindingResult bindingResult) throws ValidationServiceCustomer {
        try {
            if (bindingResult.hasErrors())
                return new ResponseEntity("Campos mal puestos", HttpStatus.BAD_REQUEST);

            AgendaDto clienteDto1 = agendaService.save(agendaDto);
            return new ResponseEntity<>(clienteDto1, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity("Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/todos")
    public ResponseEntity<List<?>> getNotas(@RequestParam(name = "username", required = false) String username) {

        try {
            List<AgendaDto> clienteDtoList = agendaService.getAllAgendasUsername(username);
            return new ResponseEntity<>(clienteDtoList, HttpStatus.OK);
        } catch (DataAccessException e) {
            return new ResponseEntity("Error: " + e.getMostSpecificCause().getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @PutMapping("/actualizar")
    public ResponseEntity<?> update(@RequestBody @Valid AgendaDto agendaDto, BindingResult bindingResult) throws ValidationServiceCustomer {
        try {
            if (bindingResult.hasErrors()) return new ResponseEntity("Campos mal puestos", HttpStatus.BAD_REQUEST);
            AgendaDto clienteDto1 = agendaService.update(agendaDto);
            return new ResponseEntity<AgendaDto>(clienteDto1, HttpStatus.OK);
        } catch (DataAccessException e) {
            return new ResponseEntity("Error: " + e.getMostSpecificCause().getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/reporte")
    public ResponseEntity<List<?>> getReportes(@RequestParam(name = "username") String username,
                                               @RequestParam(name = "init") Date init,
                                               @RequestParam(name = "end") Date end) {
        try {
            List<AgendaReporteDto> agendaReporteDtosList = agendaService.getReporte(username, init, end);
            return new ResponseEntity<>(agendaReporteDtosList, HttpStatus.OK);
        } catch (DataAccessException e) {
            return new ResponseEntity("Error: " + e.getMostSpecificCause().getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/reporte2")
    public ResponseEntity<List<?>> getReportes2(@RequestParam(name = "username") String username,
                                                @RequestParam(name = "titulo") String titulo) {
        try {
            List<AgendaReporteDto> agendaReporteDtosList = agendaService.getReporte2(username, titulo);
            return new ResponseEntity<>(agendaReporteDtosList, HttpStatus.OK);
        } catch (DataAccessException e) {
            return new ResponseEntity("Error: " + e.getMostSpecificCause().getMessage(), HttpStatus.BAD_REQUEST);
        }

    }
}
