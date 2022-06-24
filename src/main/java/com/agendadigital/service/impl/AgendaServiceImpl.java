package com.agendadigital.service.impl;

import com.agendadigital.dto.AgendaDto;
import com.agendadigital.dto.AgendaReporteDto;
import com.agendadigital.dto.CategoriaDto;
import com.agendadigital.dto.ClienteDto;
import com.agendadigital.entity.Agenda;
import com.agendadigital.entity.Categoria;
import com.agendadigital.entity.Cliente;
import com.agendadigital.exception.ValidationServiceCustomer;
import com.agendadigital.repository.AgendaRepository;
import com.agendadigital.repository.RolesRespository;
import com.agendadigital.service.AgendaService;
import com.agendadigital.util.ConversionUtil;
import com.agendadigital.util.FechaUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AgendaServiceImpl implements AgendaService {
    SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
    @Autowired
    private AgendaRepository agendaRepository;
    @Autowired
    private ConversionUtil conversionUtil;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private RolesRespository rolesRespository;

    @Transactional
    @Override
    public AgendaDto save(AgendaDto agendaDto) throws ValidationServiceCustomer {
        /*Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = null;
        if (principal instanceof UserDetails) {
            userDetails = (UserDetails) principal;
        }
        if(!userDetails.getUsername().equals(agendaDto.getClienteDto().getUsername()))
            throw new ValidationServiceCustomer("El usuario enviado: " + agendaDto.getClienteDto().getUsername() + " con username logeado: " + userDetails.getUsername() + " no son iguales ", HttpStatus.PRECONDITION_FAILED);
        String username = userDetails.getUsername();*/
        String username = agendaDto.getClienteDto().getUsername();
        Optional<Agenda> clienteOptional = agendaRepository.findByClienteUsernameAndTitulo(username, agendaDto.getTitulo());
        if (clienteOptional.isPresent()) {
            throw new ValidationServiceCustomer("La nota con titulo: " + agendaDto.getTitulo() + " con username: " + username + " ya existe", HttpStatus.PRECONDITION_FAILED);
        }
        agendaDto.setEstado(1);
        agendaDto.setFechaCreacion(LocalDateTime.now());
        Date fechaVencimiento = null;
        try {
            fechaVencimiento = formato.parse(formato.format(agendaDto.getFechaVencimiento()));
        } catch (ParseException e) {
            fechaVencimiento = new Date();
        }
        agendaDto.setFechaVencimiento(agendaDto.getFechaVencimiento() != null ? fechaVencimiento : FechaUtil.sumarDiaFecha(new Date(), 7));
        agendaDto.setNotifica(agendaDto.isNotifica());
        agendaDto.setDiasNotifica((agendaDto.isNotifica() && agendaDto.getDiasNotifica() > 0) ? agendaDto.getDiasNotifica() : 1);
        agendaDto.setEstado(1);
        Agenda agenda = buildAgendaDtoToAgenda(agendaDto);
        agenda = agendaRepository.save(agenda);
        return buildAgendaToAgendaDto(agenda);
    }

    @Transactional
    @Override
    public AgendaDto update(AgendaDto agendaDto) throws ValidationServiceCustomer {
        if (agendaDto.getIdAgenda() == null)
            throw new ValidationServiceCustomer("EL id de la nota debe estar presente", HttpStatus.BAD_REQUEST);
        /*Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = null;
        if (principal instanceof UserDetails) {
            userDetails = (UserDetails) principal;
        }
        */
        // String username = userDetails.getUsername();
        String username = agendaDto.getClienteDto().getUsername();
        Optional<Agenda> clienteOptional = agendaRepository.findById(agendaDto.getIdAgenda());
        if (!clienteOptional.isPresent()) {
            throw new ValidationServiceCustomer("La nota con titulo: " + agendaDto.getTitulo() + " no se encuentra registrada", HttpStatus.PRECONDITION_FAILED);
        }
        //Agenda agenda = modelMapper.map(agendaDto, Agenda.class);
        Agenda agenda = buildAgendaDtoToAgenda(agendaDto);
        if (validateAgendaUpdate(clienteOptional, agendaDto, username)) {
            agenda = agendaRepository.save(agenda);
        }
        return buildAgendaToAgendaDto(agenda);
    }

    @Override
    public List<AgendaDto> getAllAgendasUsername(String username2) {
        /*Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = null;
        if (principal instanceof UserDetails) {
            userDetails = (UserDetails) principal;
        }
        String username = userDetails.getUsername();*/
        String username = username2;
        List<AgendaDto> agendaDtoList = new ArrayList<>();
        List<Agenda> notas = agendaRepository.getAgendaByUsername(username == null ? null : username);
        notas.stream().forEach(nota -> {
            agendaDtoList.add(buildAgendaToAgendaDto(nota));
        });
        return agendaDtoList;
    }

    @Override
    public boolean delete(Integer id) {
        if (id == null)
            throw new ValidationServiceCustomer("EL id de la nota debe estar presente", HttpStatus.BAD_REQUEST);

        Optional<Agenda> clienteOptional = agendaRepository.findById(id);
        if (!clienteOptional.isPresent()) {
            throw new ValidationServiceCustomer("La nota no se encuentra registrada", HttpStatus.PRECONDITION_FAILED);
        }
        agendaRepository.delete(clienteOptional.get());
        return true;
    }

    @Override
    public List<AgendaReporteDto> getReporte(String username, Date init, Date end) {
        List<Object[]> reports = agendaRepository.getReporte(username, init, end);
        List<AgendaReporteDto> agendaReporteDtoList = new ArrayList<>();
        reports.stream().forEach(objects -> {
            try {
                agendaReporteDtoList.add(buildReport(objects));
            } catch (ParseException e) {

            }
        });
        return agendaReporteDtoList;
    }

    @Override
    public List<AgendaReporteDto> getReporte2(String username, String titulo) {
        return agendaRepository.getReporte2(username);
    }

    private AgendaReporteDto buildReport(Object[] obj) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = format.parse(obj[2].toString());
        AgendaReporteDto agendaReporteDto = new AgendaReporteDto();
        agendaReporteDto.setTitulo(obj[0].toString());
        agendaReporteDto.setDescripcion(obj[1].toString());
        agendaReporteDto.setFechaCreacion(date);
        agendaReporteDto.setCliente(obj[3].toString());
        agendaReporteDto.setCategoria(obj[4].toString());

        return agendaReporteDto;
    }


    private boolean validateAgendaUpdate(Optional<Agenda> agendaDb, AgendaDto agendaDto, String username) {
        if (agendaDb.get().getIdAgenda().equals(agendaDto.getIdAgenda())
                && agendaDb.get().getTitulo().equals(agendaDto.getTitulo()))
            return true;
        else {
            Optional<Agenda> agendaTitulo = agendaRepository.findByClienteUsernameAndTitulo(username, agendaDto.getTitulo());
            if (agendaTitulo.isPresent()
                    && agendaTitulo.get().getIdAgenda() != agendaDb.get().getIdAgenda())
                throw new ValidationServiceCustomer("La nota con titulo " + agendaDto.getTitulo() + " ya se encuentra registrada", HttpStatus.PRECONDITION_FAILED);
            return true;
        }
    }

    private Agenda buildAgendaDtoToAgenda(AgendaDto agendaDto) {
        Agenda agenda = modelMapper.map(agendaDto, Agenda.class);
        Cliente cliente = modelMapper.map(agendaDto.getClienteDto(), Cliente.class);
        Categoria categoria = modelMapper.map(agendaDto.getCategoriaDto(), Categoria.class);
        agenda.setCliente(cliente);
        agenda.setCategoria(categoria);
        agenda.getAlertaAgendaList().forEach(alertaAgenda -> {
            alertaAgenda.setAgenda(agenda);
            alertaAgenda.setFechaNotifica(LocalDateTime.now());
        });
        return agenda;
    }

    private AgendaDto buildAgendaToAgendaDto(Agenda agenda) {
        AgendaDto agendaDto = modelMapper.map(agenda, AgendaDto.class);
        ClienteDto clienteDto = modelMapper.map(agenda.getCliente(), ClienteDto.class);
        CategoriaDto categoriaDto = modelMapper.map(agenda.getCategoria(), CategoriaDto.class);
        agendaDto.setClienteDto(clienteDto);
        agendaDto.setCategoriaDto(categoriaDto);
        return agendaDto;
    }
}
