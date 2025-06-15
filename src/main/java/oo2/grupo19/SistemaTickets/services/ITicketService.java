package oo2.grupo19.SistemaTickets.services;

import java.time.LocalDate;
import java.util.Set;

import oo2.grupo19.SistemaTickets.dto.EstadoTicketDTO;
import oo2.grupo19.SistemaTickets.dto.PrioridadDTO;
import oo2.grupo19.SistemaTickets.dto.TicketClientDTO;
import oo2.grupo19.SistemaTickets.dto.TicketDTO;
import oo2.grupo19.SistemaTickets.dto.TicketEmployeeDTO;

public interface ITicketService extends IService<TicketDTO> {
    TicketDTO findByIdAndEmpleado(Long idEmpleado, Long idTicket);
    void actualizarEstadoTicket(Long idEmpleado, Long idTicket, EstadoTicketDTO nuevoEstado);
    void actualizarPrioridadTicket(Long idEmpleado, Long idTicket, PrioridadDTO prioridad);
    Set<TicketDTO> findTicketByCliente(String email);
    Set<TicketDTO> findTicketByAsunto(String asunto);
    Set<TicketDTO> findTicketByPrioridad(PrioridadDTO prioridad);
    Set<TicketDTO> findTicketByEmpleado(String email);
    Set<TicketDTO> findTicketByEstado(EstadoTicketDTO estado);
    Set<TicketDTO> findTicketByFechaHora(LocalDate fecha);
    Set<TicketClientDTO> traerParaCliente(String email);
    Set<TicketDTO> traerPorEstado(String estado);
    TicketClientDTO getTicketParaCliente(Long ticketId, String clienteEmail);
    TicketEmployeeDTO getTicketparaEmpleado(Long ticketId, String empleadoEmail);
    TicketEmployeeDTO asignarTicket(Long ticketId, String empleadoEmail);
}