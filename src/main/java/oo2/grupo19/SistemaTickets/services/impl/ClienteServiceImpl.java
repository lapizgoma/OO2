package oo2.grupo19.SistemaTickets.services.impl;

import java.util.List;
import java.util.Optional;

import javax.management.RuntimeErrorException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.log4j.Log4j2;
import oo2.grupo19.SistemaTickets.entities.Cliente;
import oo2.grupo19.SistemaTickets.repositories.ICliente;
import oo2.grupo19.SistemaTickets.services.IService;

@Service
@Log4j2
public class ClienteServiceImpl implements IService<Cliente> {

    private final ICliente clienteRepository;

    public ClienteServiceImpl(ICliente clienteRepository) {
        this.clienteRepository = clienteRepository;
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
            Optional<Cliente> user = clienteRepository.findByContactoEmail(object.getContacto().getEmail());
            if(user.isEmpty()){
                object.asignarContactoUsuario();
                clienteRepository.save(object);
            }else{
                log.info("El usuario ya existe en la bd!");
            }
        }catch(Error e){
            throw new RuntimeErrorException(e,"No se ha podido actualizar/insertar el usuario");
        }
    }

    @Transactional(readOnly = true)
    public Optional<Cliente> findByEmail(String email){
        return clienteRepository.findByContactoEmail(email);
    }
    
}
