package com.agendadigital.repository;

import com.agendadigital.entity.Agenda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AgendaRepository extends JpaRepository<Agenda, Integer> {

    @Query("SELECT a FROM Agenda a  WHERE (:username is null or a.cliente.username =: username)")
    List<Agenda> getAgendaByUsername(@Param("username") String username);

    @Query("SELECT a FROM Agenda a  WHERE (:titulo is null or a.titulo LIKE : titulo)")
    List<Agenda> getAgendaByLikeAndTitulo(@Param("titulo") String titulo);

    Optional<Agenda> findByClienteUsernameAndTitulo(String username, String titulo);


}
