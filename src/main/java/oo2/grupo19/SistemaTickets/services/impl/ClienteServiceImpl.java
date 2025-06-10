package oo2.grupo19.SistemaTickets.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.log4j.Log4j2;
import oo2.grupo19.SistemaTickets.entities.Cliente;
import oo2.grupo19.SistemaTickets.entities.Usuario;
import oo2.grupo19.SistemaTickets.exceptions.UserCustomExceptions;
import oo2.grupo19.SistemaTickets.repositories.ICliente;
import oo2.grupo19.SistemaTickets.repositories.IUsuario;

@Service
@Log4j2
public class ClienteServiceImpl implements oo2.grupo19.SistemaTickets.services.ICliente {

    private final ICliente clienteRepository;
    private final IUsuario usuarioRepository;

    public ClienteServiceImpl(ICliente clienteRepository, IUsuario usuarioRepository) {
        this.clienteRepository = clienteRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        clienteRepository.deleteById(id);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<Cliente> findById(Long id) {
        return clienteRepository.findById(id);
    }

    @Override
    @Transactional
    public void save(Cliente object) {
        try{
            Optional<Cliente> user = clienteRepository.findByContacto_Email(object.getContacto().getEmail());
            if(user.isEmpty()){
                object.asignarContactoUsuario();
                clienteRepository.save(object);
            } else {
                log.info("El usuario ya existe en la bd!");
                throw new UserCustomExceptions.ClienteAlreadyExistsException("El cliente con email " + object.getContacto().getEmail() + " ya existe.");
            }
        } catch (Exception e) {
            throw new UserCustomExceptions.ClienteServiceException("No se ha podido actualizar/insertar el usuario", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Cliente> findByEmail(String email){
        return clienteRepository.findByContacto_Email(email);
    }


    @Override
    @Modifying
    @Transactional(readOnly = false)
    public void eliminarCliente (String email) 
    {
        Cliente clienteEntity = clienteRepository.findByContacto_Email(email)
        .orElseThrow(() -> new UserCustomExceptions.UserNotFoundException("Cliente no encontrado :/"));

        ((Usuario) clienteEntity).setDeleted(true);
        usuarioRepository.save(clienteEntity);
    }
    
}
