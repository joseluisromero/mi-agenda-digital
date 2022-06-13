package com.agendadigital.util;

import com.agendadigital.dto.CategoriaDto;
import com.agendadigital.entity.Categoria;
import org.springframework.stereotype.Component;

@Component
public class CategoriaUtil {

    public Categoria buildCategoriaDtoToCategoria(CategoriaDto categoriaDto) {
        return Categoria.builder()
                .idCategoria(categoriaDto.getIdCategoria())
                .nombre(categoriaDto.getNombre()).build();
    }

    public CategoriaDto buildCategoriaToCategoriaDto(Categoria categoria) {
        return CategoriaDto.builder()
                .idCategoria(categoria.getIdCategoria())
                .nombre(categoria.getNombre()).build();
    }
}
