package oo2.grupo19.SistemaTickets.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import oo2.grupo19.SistemaTickets.entities.Usuario;

public interface IUsuario extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByContactoEmail(String email);
    Optional<Usuario> findByNombre(String username);
}
