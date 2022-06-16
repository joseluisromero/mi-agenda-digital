package com.agendadigital.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "cliente")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(of = "identificacion")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCliente;
    @Column(name = "identificacion", unique = true)
    private String identificacion;
    @NotBlank
    private String nombres;
    @NotBlank
    private String apellidos;
    @NotBlank
    @Column(name = "email", unique = true)
    private String email;
    @Column(name = "username", unique = true)
    private String username;

    private String password;
    private Integer estado;
    //joinColumns = @JoinColumn(name = "id_cliente") es el campo que estara  en la tabla de rompimiento
    //inverseJoinColumns = @JoinColumn(name = "id_roles") el otro  campo de la tabla de rompimiento

    @NotNull
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "cliente_roles", joinColumns = @JoinColumn(name = "id_cliente"),
            inverseJoinColumns = @JoinColumn(name = "id_roles"))
    private Set<Roles> roles = new HashSet<>();
    //@OneToMany(mappedBy = "cliente")
    //private Set<Agenda> agendaList = new HashSet<>();

}
