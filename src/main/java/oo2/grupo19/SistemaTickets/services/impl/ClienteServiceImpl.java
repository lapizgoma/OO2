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
import oo2.grupo19.SistemaTickets.dto.mappers.ContactoMapper;
import oo2.grupo19.SistemaTickets.dto.mappers.PersonaJuridicaMapper;
import oo2.grupo19.SistemaTickets.entities.Cliente;
import oo2.grupo19.SistemaTickets.entities.Contacto;
import oo2.grupo19.SistemaTickets.entities.PersonaJuridica;
import oo2.grupo19.SistemaTickets.exceptions.StatusCustomExceptions.*;
import oo2.grupo19.SistemaTickets.repositories.ICliente;
import oo2.grupo19.SistemaTickets.repositories.IContacto;
import oo2.grupo19.SistemaTickets.repositories.IPersonaJuridica;
import oo2.grupo19.SistemaTickets.repositories.estados.IRole;


@Service
@Log4j2
public class ClienteServiceImpl implements IClienteService {

    private final ICliente clienteRepository;
    private final PasswordEncoder passwordEncoder;
    private final IRole roleRepository;
    private final IContacto contactoRepository;
    private final IPersonaJuridica personaJuridicaRepository;

    public ClienteServiceImpl(ICliente clienteRepository, PasswordEncoder passwordEncoder, IRole roleRepository, IContacto contactoRepository, IPersonaJuridica personaJuridicaRepository) {
        this.clienteRepository = clienteRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.contactoRepository = contactoRepository;
        this.personaJuridicaRepository = personaJuridicaRepository;
    }

    @Override
    @Transactional
    public void delete(String email) {
        Cliente clienteEntity = clienteRepository.findByContactoEmail(email).orElseThrow(() -> new NotFoundException("Cliente no encontrado"));
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
        Optional<Cliente> clienteOpt = clienteRepository.findByContactoEmail(clientedto.getContacto().getEmail());
        Cliente cliente = null;
        if(clienteOpt.isPresent()) {
            cliente = actualizarClienteSiEstaPresente(clientedto);
        } else {
            cliente = crearClienteSiNoEstaPresente(clientedto);
        }
        log.info("Guardando cliente: " + cliente.toString());
        clienteRepository.save(cliente);
    }

    @Override
    @Transactional(readOnly = true)
    public ClienteDTO findByEmail(String email){
        return ClienteMapper.mapToClienteDto(clienteRepository.findByContactoEmail(email).orElseThrow(() -> new NotFoundException("Cliente no encontrado")));
    }
    
    private Cliente actualizarClienteSiEstaPresente(ClienteDTO clientedto) {
        Cliente clienteNew = transformarDatosCliente(clientedto);
        Contacto contactoNew = transformarDatosContacto(clientedto,clienteNew.getContacto().getId());
        clienteNew.setContacto(contactoNew);
        contactoNew.setUsuario(clienteNew);
        log.info("Tiene organizacion? : " + clienteNew.tieneOrganizacion() + " Codigo de acceso: " + clientedto.getOrganizacion().getCodigoAcceso());
        PersonaJuridica organizacionDB = null;
        if (clienteNew.tieneOrganizacion()) {
            organizacionDB = personaJuridicaRepository.findById(clienteNew.getOrganizacion().getId()).get();
        }else if (!clientedto.getOrganizacion().getCodigoAcceso().isEmpty()){
            organizacionDB = personaJuridicaRepository.findByCodigoAcceso(clientedto.getOrganizacion().getCodigoAcceso()).get();
        }
        setearOrganizacion(clientedto, clienteNew, organizacionDB);
        log.info("Nueva entidad cliente: " + clienteNew.toString());
        return clienteNew;
    }

    private Cliente transformarDatosCliente(ClienteDTO clientedto){
        Cliente clienteDB = clienteRepository.findByContactoEmail(clientedto.getContacto().getEmail()).get();
        Cliente clienteNew = ClienteMapper.mapToClienteEntity(clientedto, clienteDB);
        clienteNew.setId(clienteDB.getId());
        return clienteNew;
    }

    private Contacto transformarDatosContacto(ClienteDTO clientedto, Long idContacto){
        Contacto contactoDB = contactoRepository.findById(idContacto).get();
        Contacto contactoNew = ContactoMapper.mapToContactoEntity(clientedto.getContacto(), contactoDB);
        contactoNew.setId(contactoDB.getId());
        return contactoNew;
    }

    private Cliente crearClienteSiNoEstaPresente(ClienteDTO clientedto) {
        Cliente cliente = ClienteMapper.mapToClienteEntity(clientedto, new Cliente());
        String passwordHash = passwordEncoder.encode(cliente.getPassword());
        cliente.agregarRoles(roleRepository.findById(1L).orElseThrow(() -> new NotFoundException("Rol no encontrado")));
        cliente.setPassword(passwordHash);
        cliente.setContacto(ContactoMapper.mapToContactoEntity(clientedto.getContacto(), new Contacto()));
        cliente.setOrganizacion(PersonaJuridicaMapper.mapToPersonaJuridicaEntity(clientedto.getOrganizacion(), new PersonaJuridica()));
        return cliente;
    }

    private void setearOrganizacion(ClienteDTO clientedto, Cliente clienteNew, PersonaJuridica organizacionDB) {
        PersonaJuridica organizacionNew = PersonaJuridicaMapper.
                                            mapToPersonaJuridicaEntity(clientedto.getOrganizacion(),
                                            (clientedto.getOrganizacion() != null) ? organizacionDB : new PersonaJuridica());
        organizacionNew.setId(organizacionDB.getId());
        clienteNew.setOrganizacion(organizacionNew);
    }
}
