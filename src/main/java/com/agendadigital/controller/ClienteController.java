package com.agendadigital.controller;

import com.agendadigital.dto.ClienteDto;
import com.agendadigital.dto.UserDto;
import com.agendadigital.exception.ValidationServiceCustomer;
import com.agendadigital.security.jwt.JwtProvider;
import com.agendadigital.service.ClienteService;
import com.agendadigital.service.RolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/cliente")
public class ClienteController {
    @Autowired
    private ClienteService clienteService;
    //securuty


    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtProvider jwtProvider;

    @PostMapping("/guardar")
    public ResponseEntity<?> save(@RequestBody @Valid ClienteDto clienteDto, BindingResult bindingResult) throws ValidationServiceCustomer {
        try {
            if (bindingResult.hasErrors())
                return new ResponseEntity("Campos mal puestos", HttpStatus.BAD_REQUEST);
            ClienteDto clienteDto1 = clienteService.save(clienteDto);
            return new ResponseEntity<ClienteDto>(clienteDto1, HttpStatus.CREATED);
        } catch (DataAccessException e) {
            return new ResponseEntity("Error: " + e.getMostSpecificCause().getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@Valid @RequestBody UserDto userDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return new ResponseEntity("Campos mal puestos", HttpStatus.BAD_REQUEST);

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDto.getUser(), userDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateToken(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        userDto.setUser(userDetails.getUsername());
        userDto.setAuthorities(userDetails.getAuthorities());
        userDto.setToken(jwt);

        return new ResponseEntity<UserDto>(userDto, HttpStatus.OK);
    }

    @GetMapping("/todos")
    public ResponseEntity<List<?>> getClientes(@RequestParam(name = "identificacion", required = false) String identificacion) {

        try {
            List<ClienteDto> clienteDtoList = clienteService.getClientes(identificacion);
            return new ResponseEntity<>(clienteDtoList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity("Error al obtener la información" + e.getCause().getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
