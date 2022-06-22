package com.agendadigital.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
@Table(name = "alerta_agenda")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(of = "idAlerta")
public class AlertaAgenda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idAlerta;
    @NotBlank(message = "El campo nombre ${message.requerido}")
    private String nombre;
    private LocalDateTime fechaNotifica;
    @ManyToOne()
    @JoinColumn(name = "id_agenda")
    private Agenda agenda;
}
