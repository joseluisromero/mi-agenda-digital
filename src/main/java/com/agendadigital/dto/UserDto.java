package com.agendadigital.dto;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import javax.validation.constraints.NotBlank;
import java.util.Collection;

@Data
public class UserDto {
    @NotBlank
    private String user;
    @NotBlank
    private String password;
    private String token;
    private Collection<? extends GrantedAuthority> authorities;
}
