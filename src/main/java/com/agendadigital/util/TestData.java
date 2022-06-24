package com.agendadigital.util;

import com.agendadigital.dto.AgendaDto;
import com.agendadigital.dto.CategoriaDto;
import com.agendadigital.dto.ClienteDto;
import com.agendadigital.entity.Agenda;
import com.agendadigital.entity.Categoria;
import com.agendadigital.entity.Cliente;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Date;

@Data
public class TestData {
    public AgendaDto getAgendaDto() {
        LocalDateTime fechaVenc = LocalDateTime.of(2022, Month.FEBRUARY, 12, 14, 15);
        return AgendaDto.builder()
                .idAgenda(Integer.rotateLeft(1, 10))
                .titulo("Primera nota")
                .descripcion("descripcion test")
                .estado(1)
                .diasNotifica(2)
                .fechaCreacion(LocalDateTime.now())
                .fechaVencimiento(new Date())
                .notifica(true)
                .clienteDto(getClienteDto())
                .categoriaDto(getCategoriaDto())
                .alertaAgendaList(new ArrayList<>())
                .build();
    }

    public ClienteDto getClienteDto() {
        LocalDateTime fechaVenc = LocalDateTime.of(2022, Month.FEBRUARY, 12, 14, 15);
        return ClienteDto.builder()
                .idCliente(Integer.rotateLeft(1, 20))
                .estado(1)
                .nombres("Juan Jose")
                .apellidos("Rodriguez")
                .username("jjrodriguez")
                .identificacion("12345678876")
                .email("jj@gmail.com").build();
    }

    public CategoriaDto getCategoriaDto() {
        return CategoriaDto.builder()
                .idCategoria(Integer.rotateLeft(1, 5))
                .nombre("Estudio")
                .build();
    }

    public Agenda getAgenda(AgendaDto agendaDto) {

        return Agenda.builder()
                .idAgenda(agendaDto.getIdAgenda())
                .titulo(agendaDto.getTitulo())
                .descripcion(agendaDto.getDescripcion())
                .estado(agendaDto.getEstado())
                .diasNotifica(agendaDto.getDiasNotifica())
                .fechaCreacion(agendaDto.getFechaCreacion())
                .fechaVencimiento(agendaDto.getFechaVencimiento())
                .notifica(agendaDto.isNotifica())
                .cliente(getCliente(agendaDto.getClienteDto()))
                .categoria(getCategoria(agendaDto.getCategoriaDto()))
                .build();
    }

    public Cliente getCliente(ClienteDto clienteDto) {
        LocalDateTime fechaVenc = LocalDateTime.of(2022, Month.FEBRUARY, 12, 14, 15);
        return Cliente.builder()
                .idCliente(clienteDto.getIdCliente())
                .estado(clienteDto.getEstado())
                .nombres(clienteDto.getNombres())
                .apellidos(clienteDto.getApellidos())
                .username(clienteDto.getUsername())
                .identificacion(clienteDto.getIdentificacion())
                .email(clienteDto.getEmail()).build();
    }

    public Categoria getCategoria(CategoriaDto categoriaDto) {
        return Categoria.builder()
                .idCategoria(categoriaDto.getIdCategoria())
                .nombre(categoriaDto.getNombre())
                .build();
    }
}
