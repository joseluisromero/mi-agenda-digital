package com.agendadigital.service;

import com.agendadigital.dto.AgendaDto;
import com.agendadigital.entity.Agenda;
import com.agendadigital.entity.Cliente;
import com.agendadigital.exception.ValidationServiceCustomer;
import com.agendadigital.repository.AgendaRepository;
import com.agendadigital.repository.RolesRespository;
import com.agendadigital.service.impl.AgendaServiceImpl;
import com.agendadigital.util.TestData;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.modelmapper.ModelMapper;
import org.modelmapper.ValidationException;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.ThrowableAssert.catchThrowableOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AgendaServiceTest {

    @InjectMocks
    private AgendaServiceImpl agendaService;

    @Mock
    private AgendaRepository agendaRepository;

    @Mock
    private RolesRespository rolesRespository;

    @Spy
    private ModelMapper modelMapper;

    private TestData testData;

    @BeforeEach
    public void setUp() {
        testData = new TestData();
    }

    @Test
    public void shouldSaveSuccess() {
        AgendaDto agendaDto = testData.getAgendaDto();
        Agenda agenda = testData.getAgenda(agendaDto);
        agenda.setIdAgenda(1);
        agendaDto.setIdAgenda(null);

        //ARRANGE
        when(agendaRepository.findByClienteUsernameAndTitulo(any(), any())).thenReturn(Optional.empty());
        when(agendaRepository.save(any())).thenReturn(agenda);
        //otra forma  de retornar un guardado de la  base
        /*when(agendaRepository.save(any())).thenAnswer(new Answer<Object>() {

            public Agenda answer(InvocationOnMock invocation) throws Throwable {
                Agenda agenda = (Agenda) invocation.getArgument(0);
                agenda.setIdAgenda(1);
                agenda.setCliente(Cliente.builder()
                        .idCliente(1)
                        .estado(1)
                        .nombres("Pablo")
                        .apellidos("perez")
                        .username("pperez")
                        .identificacion("23456234")
                        .email("hdhfhhf@gmail.com").build());
                agenda.getAlertaAgendaList().forEach(address -> {
                    Assertions.assertThat(address.getNombre()).isNotNull();
                    Assertions.assertThat(address.getFechaNotifica()).isNotNull();
                });
                return agenda;
            }

        });*/
        //ACT
        AgendaDto agendaDto1 = agendaService.save(agendaDto);
        //ASSERT
        verify(agendaRepository, timeout(1)).save(any());
        Assertions.assertThat(agendaDto1.getIdAgenda()).isNotNull();
        Assertions.assertThat(agendaDto1.getClienteDto()).isNotNull();
        Assertions.assertThat(agendaDto1.getCategoriaDto()).isNotNull();
    }

    @Test
    public void shouldReturnMessageNoteExists() {
        //ARRANGE
        AgendaDto agendaDto = testData.getAgendaDto();
        Agenda agenda = testData.getAgenda(agendaDto);
        agenda.setIdAgenda(1);
        agendaDto.setIdAgenda(null);
        when(agendaRepository.findByClienteUsernameAndTitulo(any(), any())).thenReturn(Optional.of(agenda));

        //ACT
        ValidationServiceCustomer exception = catchThrowableOfType(() ->
                agendaService.save(agendaDto), ValidationServiceCustomer.class);
        org.junit.jupiter.api.Assertions.assertNotNull(exception);
        Assert.assertEquals("La nota con titulo: " + agendaDto.getTitulo() + " con username: " + agendaDto.getClienteDto().getUsername() + " ya existe", exception.getMessage());
    }

    @Test
    public void shouldUpdateSuccess() {
        //ARRANGE
        AgendaDto agendaDto = testData.getAgendaDto();
        Agenda agenda = testData.getAgenda(agendaDto);
        agenda.setIdAgenda(1);
        agendaDto.setIdAgenda(agenda.getIdAgenda());
        when(agendaRepository.findById(any())).thenReturn(Optional.of(agenda));
        when(agendaRepository.save(any())).thenReturn(agenda);
        //ACT
        AgendaDto agendaDto1 = agendaService.update(agendaDto);
        //ASSERT
        verify(agendaRepository, timeout(1)).save(any());
        Assertions.assertThat(agendaDto1.getIdAgenda()).isNotNull();
        Assertions.assertThat(agendaDto1.getClienteDto()).isNotNull();
        Assertions.assertThat(agendaDto1.getCategoriaDto()).isNotNull();
    }

    @Test
    public void shouldUpdateSuccessDiferenteTittle() {
        //ARRANGE
        AgendaDto agendaDto = testData.getAgendaDto();
        Agenda agenda = testData.getAgenda(agendaDto);
        agenda.setIdAgenda(1);
        agenda.setTitulo(agendaDto.getTitulo() + " db");
        agendaDto.setIdAgenda(agenda.getIdAgenda());
        when(agendaRepository.findById(any())).thenReturn(Optional.of(agenda));
        when(agendaRepository.save(any())).thenReturn(agenda);
        //ACT
        AgendaDto agendaDto1 = agendaService.update(agendaDto);
        //ASSERT
        verify(agendaRepository, timeout(1)).save(any());
        Assertions.assertThat(agendaDto1.getIdAgenda()).isNotNull();
        Assertions.assertThat(agendaDto1.getClienteDto()).isNotNull();
        Assertions.assertThat(agendaDto1.getCategoriaDto()).isNotNull();
    }

    @Test
    public void shouldReturnMessageIdRequired() {
        //ARRANGE
        AgendaDto agendaDto = testData.getAgendaDto();
        agendaDto.setIdAgenda(null);
        //ACT
        ValidationServiceCustomer exception = catchThrowableOfType(() ->
                agendaService.update(agendaDto), ValidationServiceCustomer.class);
        //ASSERT
        Assert.assertNotNull(exception);
        Assert.assertEquals("EL id de la nota debe estar presente", exception.getMessage());
    }


    @Test
    public void shouldReturnMessageIdNotExists() {
        //ARRANGE
        AgendaDto agendaDto = testData.getAgendaDto();
        Agenda agenda = testData.getAgenda(agendaDto);
        agenda.setIdAgenda(1);
        agendaDto.setIdAgenda(agenda.getIdAgenda());

        when(agendaRepository.findById(any())).thenReturn(Optional.empty());
        //ACT
        ValidationServiceCustomer exception = catchThrowableOfType(() ->
                agendaService.update(agendaDto), ValidationServiceCustomer.class);
        //ASSERT
        Assert.assertNotNull(exception);
        Assert.assertEquals("La nota con titulo: " + agendaDto.getTitulo() + " no se encuentra registrada", exception.getMessage());
    }

    @Test
    public void shouldListAllNotesUsername() {
        //ARRANGE
        Agenda agenda = testData.getAgenda(testData.getAgendaDto());
        agenda.setIdAgenda(1);

        Agenda agenda2 = testData.getAgenda(testData.getAgendaDto());
        agenda2.setIdAgenda(2);
        agenda2.setTitulo(agenda2.getTitulo() + " mod");
        List<Agenda> agendaList = new ArrayList<>();
        agendaList.add(agenda);
        agendaList.add(agenda2);
        when(agendaRepository.getAgendaByUsername(any())).thenReturn(agendaList);
        //ACT
        List<AgendaDto> agendaDtoList = agendaService.getAllAgendasUsername(any());
        //ASSERT
        verify(agendaRepository, timeout(1)).getAgendaByUsername(any());
        agendaDtoList.stream().forEach(agendaDto -> {
            Assertions.assertThat(agendaDto.getIdAgenda()).isNotNull();
            Assertions.assertThat(agendaDto.getClienteDto()).isNotNull();
            Assertions.assertThat(agendaDto.getCategoriaDto()).isNotNull();
        });

    }


}
