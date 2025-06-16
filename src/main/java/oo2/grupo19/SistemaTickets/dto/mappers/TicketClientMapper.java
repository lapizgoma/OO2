package oo2.grupo19.SistemaTickets.dto.mappers;

import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import oo2.grupo19.SistemaTickets.dto.TicketClientDTO;
import oo2.grupo19.SistemaTickets.dto.IntervencionDTO;
import oo2.grupo19.SistemaTickets.entities.Ticket;
import oo2.grupo19.SistemaTickets.entities.Intervencion;

public final class TicketClientMapper {
    public static TicketClientDTO mapToTicketClientDto(Ticket ticket) {
        if (ticket == null) {
            return null;
        }
        TicketClientDTO dto = new TicketClientDTO();
        dto.setId(ticket.getId());
        dto.setAsunto(ticket.getAsunto());
        dto.setDetalle(ticket.getDetalle());
        if (ticket.getFechaHora() != null) {
            dto.setFechaHoraCreado(ticket.getFechaHora().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
        }
        // Intervenciones
        Set<IntervencionDTO> intervenciones = new HashSet<>();
        if (ticket.getLstIntervencion() != null) {
            for (Intervencion intervencion : ticket.getLstIntervencion()) {
                intervenciones.add(IntervencionMapper.mapToIntervencionDto(intervencion));
            }
        }
        dto.setIntervenciones(intervenciones);
        // Estado
        dto.setEstado(ticket.getEstado() != null ? ticket.getEstado().getEstado() : null);
        // Datos del cliente
        if (ticket.getCreadoPor() != null) {
            dto.setUsuarioNombre(ticket.getCreadoPor().getNombre());
            dto.setUsuarioApellido(ticket.getCreadoPor().getApellido());
        }
        return dto;
    }

    public static Set<TicketClientDTO> mapToTicketDtoSet(List<Ticket> tickets) {
        return tickets == null ? Set.of() : tickets.stream().map(TicketClientMapper::mapToTicketClientDto).collect(Collectors.toSet());
    }
}
