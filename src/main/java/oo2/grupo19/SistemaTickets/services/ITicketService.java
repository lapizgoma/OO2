package oo2.grupo19.SistemaTickets.services;

import java.util.Map;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import oo2.grupo19.SistemaTickets.dto.TicketClientDTO;
import oo2.grupo19.SistemaTickets.dto.TicketDTO;
import oo2.grupo19.SistemaTickets.dto.TicketEmployeeDTO;
import oo2.grupo19.SistemaTickets.entities.Ticket;
import oo2.grupo19.SistemaTickets.entities.estados.EstadoTicket;
import oo2.grupo19.SistemaTickets.entities.estados.Prioridad;


public interface ITicketService extends IService<Ticket> {

    Ticket findByIdAndEmpleado(Long idEmpleado, Long idTicket);
    void actualizarEstadoTicket(Long idEmpleado, Long idTicket, EstadoTicket nuevoEstado);
    public List<Ticket> findTicketByCliente(String email);
    public List<Ticket> findTicketByAsunto(String asunto);
    public List<Ticket> findTicketByPrioridad(Prioridad prioridad);
    public List<Ticket> findTicketByEmpleado(String email);
    public List<Ticket> findTicketByEstado(EstadoTicket estado);
    public List<Ticket> findTicketByFechaHora(LocalDate fecha);
    public List<Ticket> traerPorClienteCerrado(String email);
    public List<Ticket> traerPorEstados (long idEstado);
    public TicketClientDTO getTicketParaCliente(Long ticketId, String clienteEmail);
    public TicketEmployeeDTO getTicketparaEmpleado (Long ticketId, String empleadoEmail);
    public TicketEmployeeDTO asignarTicket (Long ticketId, String empleadoEmail);
}