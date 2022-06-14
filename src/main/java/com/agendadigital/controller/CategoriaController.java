package com.agendadigital.controller;

import com.agendadigital.dto.CategoriaDto;
import com.agendadigital.exception.ValidationServiceCustomer;
import com.agendadigital.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/categoria")
public class CategoriaController {
    @Autowired
    private CategoriaService categoriaService;

    @PostMapping("/guardar")
    public ResponseEntity<CategoriaDto> save(@RequestBody @Valid CategoriaDto categoriaDto) throws ValidationServiceCustomer {
        try {
            CategoriaDto categoriaDto1 = categoriaService.save(categoriaDto);
            return new ResponseEntity<CategoriaDto>(categoriaDto1, HttpStatus.CREATED);
        } catch (DataAccessException e) {
            return new ResponseEntity("Error: " + e.getMostSpecificCause().getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/todos")
    public ResponseEntity<List<?>> getCategorias(@RequestParam(name = "nombre",required = false) String nombre) {

        try {
            List<CategoriaDto> categoriaDtoList = categoriaService.getCategorias(nombre);
            return new ResponseEntity<>(categoriaDtoList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity("Error al obtener la información" + e.getCause().getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
