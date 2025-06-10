package oo2.grupo19.SistemaTickets.repositories;

import java.util.Optional;

import oo2.grupo19.SistemaTickets.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import oo2.grupo19.SistemaTickets.entities.Cliente;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ICliente extends JpaRepository<Cliente,Long> {
    @Query("SELECT c FROM Cliente c WHERE c.contacto.email = :email")
    Optional<Cliente> findByContacto_Email(@Param("email") String email);
    Optional<Cliente> findByNombre(String username);
}
