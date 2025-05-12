package oo2.grupo19.SistemaTickets.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import oo2.grupo19.SistemaTickets.entities.Cliente;

public interface ICliente extends JpaRepository<Cliente,Long> {
    Optional<Cliente> findByContactoEmail(String email);
    Optional<Cliente> findByNombre(String username);
}
