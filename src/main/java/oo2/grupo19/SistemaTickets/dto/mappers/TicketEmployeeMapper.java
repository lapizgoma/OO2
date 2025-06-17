package oo2.grupo19.SistemaTickets.dto.mappers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.extern.log4j.Log4j2;
import oo2.grupo19.SistemaTickets.dto.TicketEmployeeDTO;
import oo2.grupo19.SistemaTickets.entities.Ticket;

@Log4j2
public final class TicketEmployeeMapper {
    public static TicketEmployeeDTO mapToTicketEmployeeDto(Ticket ticket) {
        if (ticket == null) {
            return null;
        }
        TicketEmployeeDTO dto = new TicketEmployeeDTO();
        dto.setId(ticket.getId());
        dto.setAsunto(ticket.getAsunto());
        dto.setDetalle(ticket.getDetalle());
        dto.setFechaHoraCreado(ticket.getFechaHora().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
        dto.setIntervenciones(IntervencionMapper.mapToIntervencionDtoSet(ticket.getLstIntervencion().stream().collect(Collectors.toList())));
        dto.setEstado(EstadoTicketMapper.mapEstadoTicketToDto(ticket.getEstado()));
        if (ticket.getPrioridad() != null) 
        {
            dto.setPrioridad(PrioridadMapper.mapPrioridadToDto(ticket.getPrioridad()));
        }
        dto.setCliente(ClienteMapper.mapToClienteDto(ticket.getCreadoPor()));
        // log.info ("MAPPER SUCCESS: " + ticket.getId().toString ());
        return dto;
    }

    public static Ticket mapToTicketEntity(TicketEmployeeDTO dto, Ticket ticket) {
        if (dto == null) return null;
        ticket.setId(dto.getId());
        ticket.setAsunto(dto.getAsunto());
        ticket.setDetalle(dto.getDetalle());
        ticket.setFechaHora(LocalDateTime.parse(dto.getFechaHoraCreado(), DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
        return ticket;
    }

    public static Set<TicketEmployeeDTO> mapToTicketEmployeeDtoSet(List<Ticket> tickets) {
        return tickets == null ? Set.of() : tickets.stream().map(TicketEmployeeMapper::mapToTicketEmployeeDto).collect(Collectors.toSet());
    }
}
