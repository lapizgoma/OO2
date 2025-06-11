package oo2.grupo19.SistemaTickets.services;

<<<<<<< HEAD
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

=======
import java.util.Optional;

import oo2.grupo19.SistemaTickets.entities.Cliente;

public interface IClienteService extends IService<Cliente>{
    public Optional<Cliente> findByEmail(String email);
    public void eliminarCliente (String email);
    
>>>>>>> feature/wip/spring-co-mati
}
