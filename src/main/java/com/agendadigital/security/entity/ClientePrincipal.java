package com.agendadigital.security.entity;

import com.agendadigital.entity.Cliente;
import com.agendadigital.security.enums.RolType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class ClientePrincipal implements UserDetails {
    //privilegios de cada usuario

    private String identificacion;
    private String nombres;
    private String apellidos;
    private String email;
    private String username;
    private String password;
    private Integer estado;
    private Collection<? extends GrantedAuthority> authorities;

    public ClientePrincipal(String identificacion, String nombres, String apellidos, String email, String username, String password, Integer estado, Collection<? extends GrantedAuthority> authorities) {
        this.identificacion = identificacion;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.email = email;
        this.username = username;
        this.password = password;
        this.estado = estado;
        this.authorities = authorities;
    }

    public static ClientePrincipal build(Cliente cliente){
        List<GrantedAuthority>authorities=cliente.getRoles().stream().map(rol->new SimpleGrantedAuthority(RolType.ROLE_ADMIN.name())).collect(Collectors.toList());
        return new ClientePrincipal(cliente.getIdentificacion(),cliente.getNombres(),cliente.getApellidos(),
                cliente.getEmail(),cliente.getUsername(),cliente.getPassword(),
                cliente.getEstado(),authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
    //generamos los getter de las demas propiedades

    public String getIdentificacion() {
        return identificacion;
    }

    public String getNombres() {
        return nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getEmail() {
        return email;
    }

    public Integer getEstado() {
        return estado;
    }
}
