package oo2.grupo19.SistemaTickets.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.management.RuntimeErrorException;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import oo2.grupo19.SistemaTickets.dto.IntervencionDTO;
import oo2.grupo19.SistemaTickets.dto.TicketClientDTO;
import oo2.grupo19.SistemaTickets.dto.TicketDTO;
import oo2.grupo19.SistemaTickets.dto.TicketEmployeeDTO;
import oo2.grupo19.SistemaTickets.dto.UsuarioDTO;
import oo2.grupo19.SistemaTickets.dto.mappers.TicketMapper;
import oo2.grupo19.SistemaTickets.entities.Cliente;
import oo2.grupo19.SistemaTickets.entities.Empleado;
import oo2.grupo19.SistemaTickets.entities.Intervencion;
import oo2.grupo19.SistemaTickets.entities.Ticket;
import oo2.grupo19.SistemaTickets.entities.Usuario;
import oo2.grupo19.SistemaTickets.entities.estados.EstadoTicket;
import oo2.grupo19.SistemaTickets.exceptions.NotAuthorizedException;
import oo2.grupo19.SistemaTickets.exceptions.TicketNotFoundException;
import oo2.grupo19.SistemaTickets.exceptions.UserNotFounException;
import oo2.grupo19.SistemaTickets.repositories.ICliente;
import oo2.grupo19.SistemaTickets.repositories.IEmpleado;
import oo2.grupo19.SistemaTickets.repositories.ITicket;
import oo2.grupo19.SistemaTickets.repositories.IUsuario;
import oo2.grupo19.SistemaTickets.services.IService;


@Service
@Primary
public class TicketServiceImpl implements IService<Ticket>{
    
    private final ITicket ticketRepository;
    private final IUsuario usuarioRepository;
    private final ICliente clienteRepository;
    private final IEmpleado empleadoRepository;

    public TicketServiceImpl(ITicket ticketRepository, IUsuario usuarioRepository, ICliente clienteRepository, IEmpleado empleadoRepository) {
        this.ticketRepository = ticketRepository;
        this.usuarioRepository = usuarioRepository;
        this.clienteRepository = clienteRepository;
        this.empleadoRepository= empleadoRepository;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        try{
            ticketRepository.deleteById(id);
        }catch(Error e){
            throw new RuntimeErrorException(e, "Error no se ha podido eliminar el ticket");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Ticket> findAll() {
        try{
            return ticketRepository.findAll();
        }catch(Error e){
            throw new RuntimeErrorException(e, "Error no se ha podido mostrar la lista de tickets");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Ticket> findById(Long id) {
        try{
            return ticketRepository.findById(id);
        }catch(Error e){
            throw new RuntimeErrorException(e, "Error no se ha podido mostrar el ticket");
        }
    }

    @Override
    @Transactional
    public void save(Ticket object) {
        try{
            Usuario userDb = usuarioRepository.findById(object.getCreadoPor().getId()).orElseThrow();
            object.setCreadoPor(userDb);
            ticketRepository.save(object);
        }catch(Error e){
            throw new RuntimeErrorException(e, "Error no se ha podido actualizar/insertar el ticket");
        }
        
    }

    @Transactional(readOnly = true)
    public TicketDTO traerPorCliente(Long idCliente){
        return convertirATicketDTO(ticketRepository.findByCreadoPor_Id(idCliente));
    }

    @Transactional(readOnly = true)
    public List<TicketDTO> traerPorClienteCerrado(String email, Long idEstado){
        List<Ticket> ticket = ticketRepository.traerPorClienteCerrado(email, idEstado);
        return ticket.stream().map(t -> convertirATicketDTO(t)).collect(Collectors.toList());
    }

    private TicketDTO convertirATicketDTO(Ticket ticket) {

    TicketDTO dto = new TicketDTO();
    dto.setId(ticket.getId());
    dto.setAsunto(ticket.getAsunto());
    dto.setEstado(ticket.getEstado().toString());

    dto.setCliente(ticket.getCreadoPor().usuarioToDto());
    dto.setEmpleados(ticket.getListEmpleado().stream()
        .map(t -> t.empleadoToDto())
        .collect(Collectors.toList()));
    dto.setIntervencion(ticket.getLstIntervencion().stream()
        .map(m -> m.mensajeToDto())
        .collect(Collectors.toList()));

    return dto;
}
    @Transactional (readOnly = true)
    public List<Ticket> traerPorEstados (long idEstado) {
        return ticketRepository.traerPorEstado(idEstado);
    }


    @Transactional(readOnly = true)
    public TicketClientDTO getTicketParaCliente(Long ticketId, String clienteEmail) {
        Ticket ticketEntity = ticketRepository.findById  (ticketId).orElseThrow (() -> new TicketNotFoundException("No pudimos encontrar el Ticket que buscás :/"));
        Cliente clienteEntity = clienteRepository.findByContactoEmail(clienteEmail)
        .orElseThrow(() -> new UserNotFounException("Cliente no encontrado :/"));

        System.out.println("Ticket Entity: " + ticketEntity);
        System.out.println("Cliente Entity: " + clienteEntity);
        
        if (!ticketEntity.usuarioPertenece(clienteEntity)) {
            //  mensaje para despistar
            throw new NotAuthorizedException("No pudimos encontrar el Ticket que buscás :/");
        }

        return TicketMapper.mapToTicketClientDto(ticketEntity);
    }

    @Transactional(readOnly = true)
    public TicketEmployeeDTO getTicketparaEmpleado (Long ticketId, String empleadoEmail) {
        Ticket ticketEntity = ticketRepository.findById  (ticketId).orElseThrow (() -> new TicketNotFoundException("No pudimos encontrar el Ticket que buscás :/"));
        Empleado empleadoEntity = empleadoRepository.findByContactoEmail(empleadoEmail)
        .orElseThrow(() -> new UserNotFounException("Empleado no encontrado :/"));

        return TicketMapper.mapToTicketEmployeeDto(ticketEntity, empleadoEntity);
    }

    @Transactional(readOnly = false)
    public TicketEmployeeDTO asignarTicket (Long ticketId, String empleadoEmail) 
    {
        Ticket ticketEntity = ticketRepository.findById  (ticketId).orElseThrow (() -> new TicketNotFoundException("No pudimos encontrar el Ticket que buscás :/"));
        Empleado empleadoEntity = empleadoRepository.findByContactoEmail(empleadoEmail)
        .orElseThrow(() -> new UserNotFounException("Empleado no encontrado :/"));

        if (!ticketEntity.usuarioPertenece(empleadoEntity)) 
        {
            ticketEntity.agregarEmpleado(empleadoEntity);
        }

        ticketRepository.save(ticketEntity);

        return TicketMapper.mapToTicketEmployeeDto(ticketEntity, empleadoEntity);
    }
}
