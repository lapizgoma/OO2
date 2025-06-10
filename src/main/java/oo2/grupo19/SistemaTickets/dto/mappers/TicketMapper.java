package oo2.grupo19.SistemaTickets.dto.mappers;

import java.util.List;
import java.util.stream.Collectors;

import oo2.grupo19.SistemaTickets.dto.TicketDTO;
import oo2.grupo19.SistemaTickets.entities.Ticket;

public final class TicketMapper {
    private TicketMapper() {}

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
            dto.setCliente(ticket.getCreadoPor().usuarioToDto());
        }
        // Empleados
        if (ticket.getListEmpleado() != null) {
            dto.setEmpleados(ticket.getListEmpleado().stream().map(EmpleadoMapper::mapToEmpleadoDto).collect(Collectors.toList()));
        }
        // Intervenciones
        if (ticket.getLstIntervencion() != null) {
            dto.setIntervencion(ticket.getLstIntervencion().stream().map(IntervencioMapper::mapToIntervencionDto).collect(Collectors.toList()));
        }
        return dto;
    }

    public static Ticket mapToTicketEntity(TicketDTO dto) {
        if (dto == null) return null;
        Ticket ticket = new Ticket();
        ticket.setId(dto.getId());
        ticket.setAsunto(dto.getAsunto());
        // Estado y relaciones deben ser seteadas por el servicio según lógica de negocio
        return ticket;
    }

    public static List<TicketDTO> mapToTicketDtoList(List<Ticket> tickets) {
        return tickets == null ? List.of() :
            tickets.stream().map(TicketMapper::mapToTicketDto).collect(Collectors.toList());
    }

    public static List<Ticket> mapToTicketEntityList(List<TicketDTO> dtos) {
        return dtos == null ? List.of() :
            dtos.stream().map(TicketMapper::mapToTicketEntity).collect(Collectors.toList());
    }
}
