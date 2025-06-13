package oo2.grupo19.SistemaTickets.dto.mappers;

import java.util.Set;
import java.util.stream.Collectors;

import oo2.grupo19.SistemaTickets.dto.TicketDTO;
import oo2.grupo19.SistemaTickets.entities.Ticket;

public final class TicketMapper {
    private TicketMapper() {
    }

    public static TicketDTO mapToTicketDto(Ticket ticket) {
        if (ticket == null) {
            return null;
        }
        TicketDTO dto = new TicketDTO();
        dto.setId(ticket.getId());
        dto.setAsunto(ticket.getAsunto());
        dto.setEstado(ticket.getEstado() != null ? ticket.getEstado().getEstado() : null);
        // Cliente
        if (ticket.getCreadoPor() != null) {
            dto.setCliente(ClienteMapper.mapToClienteDto(ticket.getCreadoPor()));
        }
        // Empleados
        if (ticket.getListEmpleado() != null) {
            dto.setEmpleados(ticket.getListEmpleado().stream().map(EmpleadoMapper::mapToEmpleadoDto)
                    .collect(Collectors.toSet()));
        }
        // Intervenciones
        if (ticket.getLstIntervencion() != null) {
            dto.setIntervencion(ticket.getLstIntervencion().stream().map(IntervencionMapper::mapToIntervencionDto)
                    .collect(Collectors.toSet()));
        }
        return dto;
    }

    public static Ticket mapToTicketEntity(TicketDTO dto) {
        if (dto == null)
            return null;
        Ticket ticket = new Ticket();
        ticket.setId(dto.getId());
        ticket.setAsunto(dto.getAsunto());
        // Estado y relaciones deben ser seteadas por el servicio según lógica de
        // negocio
        return ticket;
    }

    public static Set<TicketDTO> mapToTicketDtoList(Set<Ticket> tickets) {
        return tickets == null ? Set.of()
                : tickets.stream().map(TicketMapper::mapToTicketDto).collect(Collectors.toSet());
    }

    public static Set<Ticket> mapToTicketEntityList(Set<TicketDTO> dtos) {
        return dtos == null ? Set.of() : dtos.stream().map(TicketMapper::mapToTicketEntity).collect(Collectors.toSet());
    }
}
