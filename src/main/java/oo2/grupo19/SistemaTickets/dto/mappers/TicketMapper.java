package oo2.grupo19.SistemaTickets.dto.mappers;

import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import oo2.grupo19.SistemaTickets.dto.IntervencionDTO;
import oo2.grupo19.SistemaTickets.dto.TicketDTO;
import oo2.grupo19.SistemaTickets.entities.Intervencion;
import oo2.grupo19.SistemaTickets.entities.Ticket;

public final class TicketMapper {
    public static TicketDTO mapToTicketDto(Ticket ticket) {
        if (ticket == null) {
            return null;
        }
        TicketDTO dto = new TicketDTO();
        dto.setId(ticket.getId());
        dto.setAsunto(ticket.getAsunto());
        dto.setDetalle(ticket.getDetalle());
        dto.setClienteEmail(ticket.getCreadoPor().getContacto().getEmail());
        dto.setFechaHoraCreado(ticket.getFechaHora().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
        Set<IntervencionDTO> intervenciones = new HashSet<>();
        if (ticket.getLstIntervencion() != null) {
            for (Intervencion intervencion : ticket.getLstIntervencion()) {
                intervenciones.add(IntervencionMapper.mapToIntervencionDto(intervencion));
            }
            dto.setIntervenciones(IntervencionMapper.mapToIntervencionDtoSet(ticket.getLstIntervencion().stream().collect(Collectors.toList())));
        }else{
            dto.setIntervenciones(intervenciones);
        }
        // Es necesario trabajar las entities con sets?
        // Estado
        dto.setEstado(EstadoTicketMapper.mapEstadoTicketToDto(ticket.getEstado()));
        return dto;
    }

    public static Ticket mapToTicketEntity(TicketDTO dto, Ticket ticket) {
        if (dto == null)
            return null;
        ticket.setId(dto.getId());
        ticket.setAsunto(dto.getAsunto());
        ticket.setDetalle(dto.getDetalle());
        return ticket;
    }

    public static Set<TicketDTO> mapToTicketDtoSet(List<Ticket> tickets) {
        return tickets == null ? Set.of() : tickets.stream().map(TicketMapper::mapToTicketDto).collect(Collectors.toSet());
    }
}
