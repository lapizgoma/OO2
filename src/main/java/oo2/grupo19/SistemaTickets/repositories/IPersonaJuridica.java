package oo2.grupo19.SistemaTickets.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import oo2.grupo19.SistemaTickets.entities.Cliente;
import oo2.grupo19.SistemaTickets.entities.PersonaJuridica;

public interface IPersonaJuridica extends JpaRepository<PersonaJuridica,Long> {

    Optional<PersonaJuridica> findByCuit(String cuit);

}
