package com.agendadigital.controller;

import com.agendadigital.dto.AgendaDto;
import com.agendadigital.service.AgendaService;
import com.agendadigital.util.TestData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AgendaControllerTest {

    @InjectMocks
    AgendaController agendaController = new AgendaController();
    @Mock
    AgendaService agendaService;
    private TestData testData;
    @Mock
    private BindingResult bindingResult;

    @BeforeEach
    public void init() {
        testData = new TestData();
    }

    @Test
    public void should_save_success() {
        //ARRANGE
        AgendaDto agendaDto = testData.getAgendaDto();
        when(agendaService.save(any())).thenReturn(agendaDto);
        //ACT
        agendaController.save(agendaDto, bindingResult);
        //ASSERT
        verify(agendaService, times(1)).save(any());
    }

    @Test
    public void should_return_message_Error() {
        //ARRANGE
        when(bindingResult.hasErrors()).thenReturn(true);
        //ACT
        ResponseEntity responseEntity = agendaController.save(testData.getAgendaDto(), bindingResult);
        //ASSERT
        Assertions.assertEquals("Campos mal puestos", responseEntity.getBody());
    }

   /* @Test
    public void should_return_catch_Exception() {
        //ARRANGE
        //con este funciona entrar al catch
        doThrow(new RuntimeException()).when(agendaService).save(any());
        // no funciona doThrow(new Exception()).when(agendaService).save(testData.getAgendaDto());
        //when(agendaController.save(testData.getAgendaDto(), any())).thenThrow(RuntimeException.class);
        //ACT
        //ResponseEntity responseEntity = agendaController.save(any(), any());
        //ASSERT
        //Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }*/
}
