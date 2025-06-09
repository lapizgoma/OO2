package oo2.grupo19.SistemaTickets.services.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.management.RuntimeErrorException;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import oo2.grupo19.SistemaTickets.dto.TicketDTO;
import oo2.grupo19.SistemaTickets.entities.Ticket;
import oo2.grupo19.SistemaTickets.entities.Usuario;
import oo2.grupo19.SistemaTickets.entities.estados.EstadoTicket;
import oo2.grupo19.SistemaTickets.exceptions.TicketNotFound;
import oo2.grupo19.SistemaTickets.repositories.ITicket;
import oo2.grupo19.SistemaTickets.repositories.IUsuario;
import oo2.grupo19.SistemaTickets.services.IService;
import oo2.grupo19.SistemaTickets.services.ITicketService;
import oo2.grupo19.SistemaTickets.entities.estados.Prioridad;

@Service
@Primary
public class TicketServiceImpl implements ITicketService{
    
    private final ITicket ticketRepository;
    private final IUsuario clienteRepository;

    public TicketServiceImpl(ITicket ticketRepository, IUsuario clienteRepository) {
        this.ticketRepository = ticketRepository;
        this.clienteRepository = clienteRepository;
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
            Usuario userDb = clienteRepository.findById(object.getCreadoPor().getId()).orElseThrow();
            object.setCreadoPor(userDb);
            ticketRepository.save(object);
        }catch(Error e){
            throw new RuntimeErrorException(e, "Error no se ha podido actualizar/insertar el ticket");
        }
        
    }

    @Override
    @Transactional(readOnly = true)
    public Ticket findByIdAndEmpleado(Long idEmpleado, Long idTicket) {
        try{
            return ticketRepository.traerPorEmpleadoYId(idEmpleado, idTicket);
        } catch (Exception e) {
        throw new RuntimeException("No se ha podido mostrar el ticket", e);
        }
    }

    @Override
    @Transactional
    public void actualizarEstadoTicket(Long idEmpleado, Long idTicket, EstadoTicket nuevoEstado) {
        Ticket ticket = this.findByIdAndEmpleado(idEmpleado, idTicket);
        if (ticket == null) {
          throw new TicketNotFound("No se ha encontrado el ticket o no tiene permiso");
        }
        ticket.setEstado(nuevoEstado);
        ticketRepository.save(ticket);
    }

    @Override
    @Transactional
    public List<Ticket> findTicketByCliente(String email) {
        try{
            return ticketRepository.traerPorCliente(email);
        }catch(Error e){
            throw new RuntimeErrorException(e, "Error al intentar mostrar los tickets del cliente");
        }
    }
    @Override
    @Transactional
    public List<Ticket> findTicketByAsunto(String asunto) {
        try{
            return ticketRepository.traerPorAsunto(asunto);
        }catch(Error e){
            throw new RuntimeErrorException(e, "Error al intentar mostrar los tickets ");
        }
    }

    @Override
    @Transactional
    public List<Ticket> findTicketByEmpleado(String email) {
        try{
            return ticketRepository.traerPorEmpleado(email);
        }catch(Error e){
            throw new RuntimeErrorException(e, "Error al intentar mostrar los tickets del empleado");
        }
    }
    @Override
    @Transactional
    public List<Ticket> findTicketByEstado(EstadoTicket estado) {
        try{
            return ticketRepository.traerPorEstado(estado.getId());
        }catch(Error e){
            throw new RuntimeErrorException(e, "Error al intentar mostrar los tickets del cliente");
        }
    }

    @Override
    @Transactional
    public List<Ticket> findTicketByPrioridad(Prioridad prioridad) {
        try{
            return ticketRepository.traerPorPrioridad(prioridad.getId());
        }catch(Error e){
            throw new RuntimeErrorException(e, "Error al intentar mostrar los tickets");
        }
    }

    @Override
    @Transactional
    public List<Ticket> findTicketByFechaHora(LocalDate fecha) {  
        try {
            LocalDateTime inicio = fecha.atStartOfDay();
            LocalDateTime fin = inicio.plusDays(1);
            return ticketRepository.traerPorRangoFecha(inicio, fin);
        } catch (Error e) {
        throw new RuntimeErrorException(e, "Error al intentar mostrar los tickets");
        }
    }

    @Transactional(readOnly = true)
    public TicketDTO traerPorCliente(Long idCliente){
        return convertirATicketDTO(ticketRepository.findByCreadoPor_Id(idCliente));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Ticket> traerPorClienteCerrado(String email){
        Long estado = 3L;
        List<Ticket> ticket = ticketRepository.traerPorClienteCerrado(email, estado);
        return ticket;
        //return ticket.stream().map(t -> convertirATicketDTO(t)).collect(Collectors.toList());
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

    
}
