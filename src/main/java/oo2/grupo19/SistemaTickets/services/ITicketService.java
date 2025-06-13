package oo2.grupo19.SistemaTickets.services;

import java.time.LocalDate;
import java.util.List;

import oo2.grupo19.SistemaTickets.dto.ticket.TicketDTO;
import oo2.grupo19.SistemaTickets.dto.ticket.TicketEmployeeDTO;
import oo2.grupo19.SistemaTickets.entities.Ticket;
import oo2.grupo19.SistemaTickets.entities.estados.EstadoTicket;
import oo2.grupo19.SistemaTickets.entities.estados.Prioridad;

public interface ITicketService extends IService<Ticket> {
    Ticket findByIdAndEmpleado(Long idEmpleado, Long idTicket);
    void actualizarEstadoTicket(Long idEmpleado, Long idTicket, EstadoTicket nuevoEstado);
    List<Ticket> findTicketByCliente(String email);
    List<Ticket> findTicketByAsunto(String asunto);
    List<Ticket> findTicketByPrioridad(Prioridad prioridad);
    List<Ticket> findTicketByEmpleado(String email);
    List<Ticket> findTicketByEstado(EstadoTicket estado);
    List<Ticket> findTicketByFechaHora(LocalDate fecha);
    List<Ticket> traerPorCliente(String email);
    List<Ticket> traerPorEstados(long idEstado);
    TicketDTO getTicketParaCliente(Long ticketId, String clienteEmail);
    TicketEmployeeDTO getTicketparaEmpleado(Long ticketId, String empleadoEmail);
    TicketEmployeeDTO asignarTicket(Long ticketId, String empleadoEmail);
}