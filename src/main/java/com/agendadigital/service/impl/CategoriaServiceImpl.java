package com.agendadigital.service.impl;

import com.agendadigital.dto.CategoriaDto;
import com.agendadigital.entity.Categoria;
import com.agendadigital.exception.ValidationServiceCustomer;
import com.agendadigital.repository.CategoriaRepository;
import com.agendadigital.service.CategoriaService;
import com.agendadigital.util.CategoriaUtil;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoriaServiceImpl implements CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;
    @Autowired
    private CategoriaUtil categoriaUtil;

    @Transactional
    @Override
    public CategoriaDto save(CategoriaDto categoriaDto) throws ValidationServiceCustomer{
        Optional<Categoria> categoriaOptional = categoriaRepository.findByNombre(categoriaDto.getNombre());
        if (categoriaOptional.isPresent()) {
            throw  new ValidationServiceCustomer("Categoria "+categoriaDto.getNombre()+ " ya existe", HttpStatus.PRECONDITION_FAILED);
        }
        Categoria categoria = categoriaUtil.buildCategoriaDtoToCategoria(categoriaDto);
        categoria = categoriaRepository.save(categoria);
        return categoriaUtil.buildCategoriaToCategoriaDto(categoria);
    }

    @Override
    public List<CategoriaDto> getCategorias(String nombre) {
        List<CategoriaDto> categoriaDtoList = new ArrayList<>();
        List<Categoria> categoriaSet = categoriaRepository.getCategoriaByNombre(nombre == null ? null : nombre);
        categoriaSet.stream().forEach(categoria -> {
            categoriaDtoList.add(categoriaUtil.buildCategoriaToCategoriaDto(categoria));
        });

        return categoriaDtoList;
    }
}
