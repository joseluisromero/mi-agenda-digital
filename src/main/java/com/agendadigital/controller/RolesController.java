package com.agendadigital.controller;

import com.agendadigital.dto.RolesDto;
import com.agendadigital.exception.ValidationServiceCustomer;
import com.agendadigital.service.CategoriaService;
import com.agendadigital.service.RolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/roles")
public class RolesController {
    @Autowired
    private RolesService rolesService;

    @PostMapping("/guardar")
    public ResponseEntity<RolesDto> save(@RequestBody @Valid RolesDto rolesDto) throws ValidationServiceCustomer {
        try {
            RolesDto rolesDto1 = rolesService.save(rolesDto);
            return new ResponseEntity<RolesDto>(rolesDto1, HttpStatus.CREATED);
        } catch (DataAccessException e) {
            return new ResponseEntity("Error: " + e.getMostSpecificCause().getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/todos")
    public ResponseEntity<List<?>> getCategorias(@RequestParam(name = "nombre", required = false) String nombre) {

        try {
            List<RolesDto> rolesDtoList = rolesService.getRoles();
            return new ResponseEntity<>(rolesDtoList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity("Error al obtener la información" + e.getCause().getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
