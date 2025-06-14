package oo2.grupo19.SistemaTickets.dto.mappers;

import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

import oo2.grupo19.SistemaTickets.dto.TicketEmployeeDTO;
import oo2.grupo19.SistemaTickets.dto.IntervencionDTO;
import oo2.grupo19.SistemaTickets.entities.Ticket;
import oo2.grupo19.SistemaTickets.entities.Empleado;

public final class TicketEmployeeMapper {
    public static TicketEmployeeDTO mapToTicketEmployeeDto(Ticket ticket, Empleado empleadoSolicitante) {
        if (ticket == null) {
            return null;
        }
        TicketEmployeeDTO dto = new TicketEmployeeDTO();
        dto.setId(ticket.getId());
        dto.setAsunto(ticket.getAsunto());
        dto.setDetalle(ticket.getDetalle());
        if (ticket.getFechaHora() != null) {
            dto.setFechaHoraCreado(ticket.getFechaHora().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
        }
        // Intervenciones
        Set<IntervencionDTO> intervenciones = new HashSet<>();
        if (ticket.getLstIntervencion() != null) {
            ticket.getLstIntervencion().forEach(i -> intervenciones.add(IntervencionMapper.mapToIntervencionDto(i)));
        }
        dto.setIntervenciones(intervenciones);
        // Estado y prioridad
        dto.setEstado(ticket.getEstado() != null ? ticket.getEstado().getEstado() : null);
        dto.setPrioridad(ticket.getPrioridad());
        // Datos del cliente
        if (ticket.getCreadoPor() != null) {
            dto.setUsuarioNombre(ticket.getCreadoPor().getNombre());
            dto.setUsuarioApellido(ticket.getCreadoPor().getApellido());
            if (ticket.getCreadoPor().getContacto() != null) {
                dto.setUsuarioContactoDTO(ContactoMapper.mapToContactoDto(ticket.getCreadoPor().getContacto()));
            }
        }
        // Empleado pertenece
        dto.setEmpleadoPertenece(ticket.usuarioPertenece(empleadoSolicitante));
        return dto;
    }
}
