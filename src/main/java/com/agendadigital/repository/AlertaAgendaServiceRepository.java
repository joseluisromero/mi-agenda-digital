package com.agendadigital.repository;

import com.agendadigital.entity.AlertaAgenda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlertaAgendaServiceRepository extends JpaRepository<AlertaAgenda, Integer> {
}
