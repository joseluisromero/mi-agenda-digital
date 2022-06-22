package com.agendadigital.service.impl;

import com.agendadigital.dto.CategoriaDto;
import com.agendadigital.entity.Categoria;
import com.agendadigital.exception.ValidationServiceCustomer;
import com.agendadigital.repository.CategoriaRepository;
import com.agendadigital.service.CategoriaService;
import com.agendadigital.util.ConversionUtil;
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
    private ConversionUtil conversionUtil;

    @Transactional
    @Override
    public CategoriaDto save(CategoriaDto categoriaDto) throws ValidationServiceCustomer {
        Optional<Categoria> categoriaOptional = categoriaRepository.findByNombre(categoriaDto.getNombre());
        if (categoriaOptional.isPresent()) {
            throw new ValidationServiceCustomer("Categoria " + categoriaDto.getNombre() + " ya existe", HttpStatus.PRECONDITION_FAILED);
        }
        Categoria categoria = conversionUtil.buildCategoriaDtoToCategoria(categoriaDto);
        categoria = categoriaRepository.save(categoria);
        return conversionUtil.buildCategoriaToCategoriaDto(categoria);
    }

    @Transactional
    @Override
    public CategoriaDto update(CategoriaDto categoriaDto) throws ValidationServiceCustomer {
        if (categoriaDto.getIdCategoria() == null)
            throw new ValidationServiceCustomer("Categoria " + categoriaDto.getNombre() + " no existe", HttpStatus.PRECONDITION_FAILED);
        Categoria categoria = conversionUtil.buildCategoriaDtoToCategoria(categoriaDto);
        categoria = categoriaRepository.save(categoria);
        return conversionUtil.buildCategoriaToCategoriaDto(categoria);
    }

    @Override
    public List<CategoriaDto> getCategorias(String nombre) {
        List<CategoriaDto> categoriaDtoList = new ArrayList<>();
        List<Categoria> categoriaSet = categoriaRepository.getCategoriaByNombre(nombre == null ? null : nombre);
        categoriaSet.stream().forEach(categoria -> {
            categoriaDtoList.add(conversionUtil.buildCategoriaToCategoriaDto(categoria));
        });

        return categoriaDtoList;
    }
}
