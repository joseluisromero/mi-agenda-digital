package com.agendadigital.util;

import com.agendadigital.dto.CategoriaDto;
import com.agendadigital.dto.ClienteDto;
import com.agendadigital.entity.Categoria;
import com.agendadigital.entity.Cliente;
import org.springframework.stereotype.Component;

@Component
public class ConversionUtil {

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

    public ClienteDto buildClienteToClienteDto(Cliente cliente) {
        return ClienteDto.builder()
                .idCliente(cliente.getIdCliente())
                .identificacion(cliente.getIdentificacion())
                .nombres(cliente.getNombres())
                .apellidos(cliente.getApellidos())
                .email(cliente.getEmail())
                .username(cliente.getUsername())
                .password(cliente.getPassword())
                .estado(cliente.getEstado())
                .build();
    }

    public Cliente buildClienteDtoToCliente(ClienteDto clienteDto) {
        return Cliente.builder()
                .idCliente(clienteDto.getIdCliente())
                .identificacion(clienteDto.getIdentificacion())
                .nombres(clienteDto.getNombres())
                .apellidos(clienteDto.getApellidos())
                .email(clienteDto.getEmail())
                .username(clienteDto.getUsername())
                .password(clienteDto.getPassword())
                .estado(clienteDto.getEstado())
                .build();
    }
}
