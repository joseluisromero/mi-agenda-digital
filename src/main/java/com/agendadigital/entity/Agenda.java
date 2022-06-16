package com.agendadigital.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "agenda")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(of = "idAgenda")
public class Agenda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idAgenda;
    @Column(unique = true)
    private String titulo;
    private String descripcion;
    //@Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime fechaCreacion;
    //@Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime fechaModificacion;
    @Temporal(TemporalType.DATE)
    private Date fechaVencimiento;
    @Builder.Default
    @Column(name = "")
    private boolean notifica = false;
    @Builder.Default
    private Integer diasNotifica = 0;
    @Builder.Default
    private Integer estado = 1;
    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;
    @ManyToOne
    @JoinColumn(name = "id_categoria")
    private Categoria categoria;

}
