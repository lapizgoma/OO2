package oo2.grupo19.SistemaTickets.services;

import oo2.grupo19.SistemaTickets.entities.Cliente;
import oo2.grupo19.SistemaTickets.exceptions.UserCustomExceptions;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface ICliente {

    void delete(Long id);
    List<Cliente> findAll();
    Optional<Cliente> findById(Long id);
    void save(Cliente object);
    Optional<Cliente> findByEmail(String email);
    void eliminarCliente (String email);

}
