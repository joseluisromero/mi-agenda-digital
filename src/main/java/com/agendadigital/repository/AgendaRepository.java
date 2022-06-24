package com.agendadigital.repository;

import com.agendadigital.dto.AgendaReporteDto;
import com.agendadigital.entity.Agenda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface AgendaRepository extends JpaRepository<Agenda, Integer> {

    @Query("SELECT a FROM Agenda a JOIN FETCH a.cliente c  WHERE (:username is null or a.cliente.username =: username)")
    List<Agenda> getAgendaByUsername(@Param("username") String username);

    @Query("SELECT a FROM Agenda a  WHERE (:titulo is null or a.titulo LIKE : titulo)")
    List<Agenda> getAgendaByLikeAndTitulo(@Param("titulo") String titulo);

    Optional<Agenda> findByClienteUsernameAndTitulo(String username, String titulo);

    @Query(value = " select  a.titulo ,a.descripcion ,cast(a.fecha_creacion as date)as fecha_creacion  ,(c.nombres || c.apellidos )as cliente,ctg.nombre as categoria " +
            " from agenda a " +
            " join cliente c on (c.id_cliente=a.id_cliente)" +
            " join categoria ctg on (ctg.id_categoria=a.id_categoria)" +
            " where ( :username is null or c.username = :username )  " +
            " and cast(a.fecha_creacion as date) between :init and :end " +
            " order by a.fecha_creacion desc ", nativeQuery = true)
    List<Object[]> getReporte(@Param("username") String username, @Param("init") Date init, @Param("end") Date end);

    @Query( nativeQuery = true)
    List<AgendaReporteDto> getReporte2(@Param("username") String username);

}
