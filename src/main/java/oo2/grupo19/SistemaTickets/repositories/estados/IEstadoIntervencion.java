package oo2.grupo19.SistemaTickets.repositories.estados;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import oo2.grupo19.SistemaTickets.entities.estados.EstadoIntervencion;

@Repository
public interface IEstadoIntervencion extends JpaRepository<EstadoIntervencion,Long>{
}
