package oo2.grupo19.SistemaTickets.services;

import java.util.Optional;

import oo2.grupo19.SistemaTickets.entities.Cliente;

public interface IClienteService extends IService<Cliente>{
    Optional<Cliente> findByEmail(String email);
    void eliminarCliente (String email);
}
