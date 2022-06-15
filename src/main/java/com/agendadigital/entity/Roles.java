package com.agendadigital.entity;

import com.agendadigital.security.enums.RolType;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "roles")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(of = "nombre")
public class Roles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idRoles;
    @Column(unique = true)
    @NotBlank
    private String nombre;

}
