package com.agendadigital.entity;

import lombok.*;

import javax.persistence.*;

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
    private String nombres;
    private String apellidos;
    private String email;
    private String username;
    private String password;
    private int estado;

}
