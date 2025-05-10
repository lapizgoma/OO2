package oo2.grupo19.SistemaTickets.repositories.estados;

import org.springframework.data.jpa.repository.JpaRepository;

import oo2.grupo19.SistemaTickets.entities.estados.EstadoTicket;

public interface IEstadoTicket extends JpaRepository<EstadoTicket,Long> {

}
