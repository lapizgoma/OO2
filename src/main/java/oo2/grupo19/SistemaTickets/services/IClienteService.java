package oo2.grupo19.SistemaTickets.services;

import oo2.grupo19.SistemaTickets.entities.Cliente;

import java.util.List;
import java.util.Optional;

public interface IClienteService {

    void delete(Long id);
    List<Cliente> findAll();
    Optional<Cliente> findById(Long id);
    void save(Cliente object);
    Optional<Cliente> findByEmail(String email);
    void eliminarCliente (String email);

}
