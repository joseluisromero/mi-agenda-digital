package com.agendadigital.entity;

import com.agendadigital.dto.AgendaReporteDto;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "agenda")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(of = "idAgenda")

@SqlResultSetMapping(name = "getReporte2", classes = {
        @ConstructorResult(columns = {
                @ColumnResult(name = "titulo", type = String.class),
                @ColumnResult(name = "descripcion", type = String.class),
                @ColumnResult(name = "fecha_creacion", type = Date.class),
                @ColumnResult(name = "cliente", type = String.class),
                @ColumnResult(name = "categoria", type = String.class)
        },
                targetClass = AgendaReporteDto.class
        )
})
@NamedNativeQuery(name = "Agenda.getReporte2", query = "select  a.titulo ,a.descripcion ,cast(a.fecha_creacion as date)as fecha_creacion  ,(c.nombres || c.apellidos )as cliente,ctg.nombre as categoria " +
        "             from agenda a  " +
        "             join cliente c on (c.id_cliente=a.id_cliente) " +
        "             join categoria ctg on (ctg.id_categoria=a.id_categoria) " +
        "             where ( :username is null or c.username = :username )  " +
        "             order by a.fecha_creacion desc ", resultSetMapping = "getReporte2")

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
    @Builder.Default
    @OneToMany(mappedBy = "agenda", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private List<AlertaAgenda> alertaAgendaList = new ArrayList<>();

}
