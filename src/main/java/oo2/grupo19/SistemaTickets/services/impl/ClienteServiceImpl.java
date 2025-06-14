package oo2.grupo19.SistemaTickets.services.impl;

import java.util.Optional;
import java.util.Set;

import oo2.grupo19.SistemaTickets.services.IClienteService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.log4j.Log4j2;
import oo2.grupo19.SistemaTickets.dto.ClienteDTO;
import oo2.grupo19.SistemaTickets.dto.mappers.ClienteMapper;
import oo2.grupo19.SistemaTickets.entities.Cliente;
import oo2.grupo19.SistemaTickets.exceptions.StatusCustomExceptions.*;
import oo2.grupo19.SistemaTickets.repositories.ICliente;
import oo2.grupo19.SistemaTickets.repositories.estados.IRole;


@Service
@Log4j2
public class ClienteServiceImpl implements IClienteService {

    private final ICliente clienteRepository;
    private final PasswordEncoder passwordEncoder;
    private final IRole roleRepository;

    public ClienteServiceImpl(ICliente clienteRepository, PasswordEncoder passwordEncoder, IRole roleRepository) {
        this.clienteRepository = clienteRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Cliente clienteEntity = clienteRepository.findById(id).orElseThrow(() -> new NotFoundException("Cliente no encontrado"));
        clienteEntity.setDeleted(true);
        clienteRepository.save(clienteEntity);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Set<ClienteDTO> findAll() {
        return ClienteMapper.mapToClienteDtoSet(clienteRepository.findAll());
    }
    
    @Override
    @Transactional(readOnly = true)
    public ClienteDTO findById(Long id) {
        return ClienteMapper.mapToClienteDto(clienteRepository.findById(id).orElseThrow(() -> new NotFoundException("Cliente no encontrado")));
    }

    @Override
    @Transactional
    public void save(ClienteDTO clientedto) {
        if(clientedto == null) {
            throw new IllegalArgumentException("El empleado no puede ser null");
        }
        Optional<Cliente> clienteOpt = clienteRepository.findByContactoEmail(clientedto.getEmail());
        if(clienteOpt.isPresent()) {
            clientedto.setId(clienteOpt.get().getId());
            Cliente cliente = ClienteMapper.mapToClienteEntity(clientedto);
            clienteRepository.save(cliente);
        } else {
        Cliente cliente = ClienteMapper.mapToClienteEntity(clientedto);
        String passwordHash = passwordEncoder.encode(cliente.getPassword());
        cliente.agregarRoles(roleRepository.findById(1L).orElseThrow(() -> new NotFoundException("Rol no encontrado")));
        cliente.setPassword(passwordHash);
        clienteRepository.save(cliente);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public ClienteDTO findByEmail(String email){
        return ClienteMapper.mapToClienteDto(clienteRepository.findByContactoEmail(email).orElseThrow(() -> new NotFoundException("Cliente no encontrado")));
    }
}
