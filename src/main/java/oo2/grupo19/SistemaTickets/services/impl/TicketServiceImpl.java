package oo2.grupo19.SistemaTickets.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.management.RuntimeErrorException;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import oo2.grupo19.SistemaTickets.dto.TicketDTO;
import oo2.grupo19.SistemaTickets.entities.Ticket;
import oo2.grupo19.SistemaTickets.repositories.ITicket;
import oo2.grupo19.SistemaTickets.services.IService;

@Service
@Primary
public class TicketServiceImpl implements IService<Ticket>{
    
    private final ITicket ticketRepository;

    public TicketServiceImpl(ITicket ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Override
    public void delete(Long id) {
        try{
            ticketRepository.deleteById(id);
        }catch(Error e){
            throw new RuntimeErrorException(e, "Error no se ha podido eliminar el ticket");
        }
    }

    @Override
    public List<Ticket> findAll() {
        try{
            return ticketRepository.findAll();
        }catch(Error e){
            throw new RuntimeErrorException(e, "Error no se ha podido mostrar la lista de tickets");
        }
    }

    @Override
    public Optional<Ticket> findById(Long id) {
        try{
            return ticketRepository.findById(id);
        }catch(Error e){
            throw new RuntimeErrorException(e, "Error no se ha podido mostrar el ticket");
        }
    }

    @Override
    public void save(Ticket object) {
        try{
            ticketRepository.save(object);
        }catch(Error e){
            throw new RuntimeErrorException(e, "Error no se ha podido actualizar/insertar el ticket");
        }
        
    }

    public TicketDTO traerPorCliente(Long idCliente){
        return convertirATicketDTO(ticketRepository.findByCreadoPor_Id(idCliente));
    }

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

    
}
