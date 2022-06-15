package com.agendadigital.security.service;

import com.agendadigital.entity.Cliente;
import com.agendadigital.security.entity.ClientePrincipal;
import com.agendadigital.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private ClienteService clienteService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Cliente cliente=clienteService.getByUsername(username).get();
        return ClientePrincipal.build(cliente);
    }
}
