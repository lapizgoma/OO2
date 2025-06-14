package oo2.grupo19.SistemaTickets.repositories.estados;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import oo2.grupo19.SistemaTickets.entities.estados.Prioridad;

@Repository
public interface IPrioridad extends JpaRepository<Prioridad,Long> {
    Optional<Prioridad> findByPrioridad(String prioridad);
}
