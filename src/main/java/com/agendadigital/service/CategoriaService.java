package com.agendadigital.service;

import com.agendadigital.dto.CategoriaDto;
import com.agendadigital.exception.ValidationServiceCustomer;

import java.util.List;
import java.util.Set;

public interface CategoriaService {
    CategoriaDto save(CategoriaDto categoriaDto)throws ValidationServiceCustomer;

    List<CategoriaDto> getCategorias(String nombre);
}
