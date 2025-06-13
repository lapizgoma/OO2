package oo2.grupo19.SistemaTickets.dto.mappers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.stream.Collectors;

import oo2.grupo19.SistemaTickets.dto.ticket.TicketDTO;
import oo2.grupo19.SistemaTickets.dto.ticket.TicketEmployeeDTO;
import oo2.grupo19.SistemaTickets.entities.Empleado;
import oo2.grupo19.SistemaTickets.entities.Ticket;

public final class TicketMapper 
{
    public static TicketDTO mapToTicketDto(Ticket ticket) 
    {
        if (ticket == null)
        {
            return null;
        }

        TicketDTO dto = new TicketDTO();
        dto.setId(ticket.getId());
        dto.setAsunto(ticket.getAsunto());
        dto.setDetalle(ticket.getDetalle());

        if (ticket.getFechaHora() != null)
        {
            dto.setFechaHoraCreado(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")
                    .format(ticket.getFechaHora()));
        }
        dto.setEstado(ticket.getEstado() != null ? ticket.getEstado().getEstado() : null);
        // Intervenciones
        if (ticket.getLstIntervencion() != null)
        {
            dto.setIntervenciones(ticket.getLstIntervencion().stream().map(IntervencionMapper::mapToIntervencionDto)
                    .collect(Collectors.toSet()));
        }
        return dto;
    }

    public static Ticket mapToTicketEntity(TicketDTO dto) 
    {
        if (dto == null)
        {
            return null;
        }

        Ticket ticket = new Ticket();

        ticket.setId(dto.getId());
        ticket.setAsunto(dto.getAsunto());
        ticket.setDetalle(dto.getDetalle());
        if (dto.getFechaHoraCreado() != null)
        {
            ticket.setFechaHora(LocalDateTime.parse(dto.getFechaHoraCreado(),
                    DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
        }
        // Estado y relaciones deben ser seteadas por el servicio según lógica de
        // negocio
        return ticket;
    }

    public static Set<TicketDTO> mapToTicketDtoList(Set<Ticket> tickets) 
    {
        return tickets == null ? Set.of()
                : tickets.stream().map(TicketMapper::mapToTicketDto).collect(Collectors.toSet());
    }

    public static Set<Ticket> mapToTicketEntityList(Set<TicketDTO> dtos) 
    {
        return dtos == null ? Set.of() : dtos.stream().map(TicketMapper::mapToTicketEntity).collect(Collectors.toSet());
    }

    public static TicketEmployeeDTO mapToTicketEmployeeDto(Ticket ticket, Empleado empleadoSolicitante) 
    {
        if (ticket == null)
        {
            return null;
        }

        TicketEmployeeDTO dto = new TicketEmployeeDTO();

        dto.setId(ticket.getId());
        dto.setAsunto(ticket.getAsunto());
        dto.setDetalle(ticket.getDetalle());
        if (ticket.getFechaHora() != null)
        {
            dto.setFechaHoraCreado(ticket.getFechaHora().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
        }
        dto.setEstado(ticket.getEstado() != null ? ticket.getEstado().getEstado() : null);
        dto.setPrioridad(ticket.getPrioridad().getPrioridad());
        // Empleados
        if (ticket.getListEmpleado() != null)
        {
            dto.setEmpleados(ticket.getListEmpleado().stream().map(EmpleadoMapper::mapToEmpleadoDto)
                    .collect(Collectors.toSet()));
        }
        // Intervenciones
        if (ticket.getLstIntervencion() != null)
        {
            dto.setIntervenciones(ticket.getLstIntervencion().stream().map(IntervencionMapper::mapToIntervencionDto)
                    .collect(Collectors.toSet()));
        }
        // Datos del cliente
        if (ticket.getCreadoPor() != null)
        {
            dto.setNombreCliente(ticket.getCreadoPor().getNombre());
            dto.setApellidoCliente(ticket.getCreadoPor().getApellido());
            if (ticket.getCreadoPor().getContacto() != null)
            {
                dto.setContactoClienteDTO(ContactoMapper.mapToContactoDto(ticket.getCreadoPor().getContacto()));
            }
        }
        // Empleado pertenece
        dto.setEmpleadoPertenece(ticket.usuarioPertenece(empleadoSolicitante));
        return dto;
    }
}
